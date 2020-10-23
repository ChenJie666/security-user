package oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.AdministratorMapper;
import oauth2.dao.RoleMapper;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.po.TbRolePO;
import oauth2.service.AdministratorService;
import oauth2.service.RolePermissionService;
import oauth2.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 10:28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, TbRolePO> implements RoleService {

    /**
     * 查询
     */
//    @Override
//    public ObjListPO<TbRolePO> findAllRoles(Integer pageCurrent, Integer pageSize) {
//        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
//        IPage<TbRolePO> tbRolePOIPage = baseMapper.selectPage(new Page<>(pageCurrent, pageSize), tbRolePOQueryWrapper);
//
//        Assert.notEmpty(tbRolePOIPage.getRecords(), HttpStatus.NOT_FOUND.toString());
//
//        return new ObjListPO<>(tbRolePOIPage.getCurrent(), tbRolePOIPage.getSize(), tbRolePOIPage.getPages(), tbRolePOIPage.getTotal(), tbRolePOIPage.getRecords());
//    }

//    @Override
//    public List<TbRolePO> findAllRoles() {
//        List<TbRolePO> tbRolePOs = baseMapper.selectList(new QueryWrapper<>());
//        Assert.notEmpty(tbRolePOs, HttpStatus.NOT_FOUND.toString());
//
//        return tbRolePOs;
//    }

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private AdministratorService administratorService;

    @Resource
    private AdministratorMapper administratorMapper;

    /**
     * 查找自己和子用户拥有的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<TbRolePO> findMyRoles(Integer userId) {
        // 查找所有的子用户
        Set<Integer> branchUserIds = administratorService.findBranchUserIdsByUserId(userId);
        branchUserIds.add(userId);
        // 查找自己和子用户所拥有的角色
        List<TbRolePO> tbRolePOs = roleMapper.findMyRoles(branchUserIds);

        Assert.notEmpty(tbRolePOs, HttpStatus.NOT_FOUND.toString());

        tbRolePOs.forEach(tbRolePO -> {
            // 查询创建更新者的用户名
            if (!Objects.isNull(tbRolePO.getCreatorId())) {
                String creator = administratorMapper.getUsernameById(tbRolePO.getCreatorId());
                tbRolePO.setCreatorName(creator);

                if (!Objects.isNull(tbRolePO.getUpdaterId())) {
                    if (tbRolePO.getCreatorId().equals(tbRolePO.getUpdaterId())) {
                        tbRolePO.setUpdaterName(creator);
                    } else {
                        String updater = administratorMapper.getUsernameById(tbRolePO.getUpdaterId());
                        tbRolePO.setUpdaterName(updater);
                    }
                }
            }
        });

        return tbRolePOs;
    }

//    private void findChildren(TbRolePO tbRolePO) {
//        System.out.println("*****findChildren:" + tbRolePO);
//        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
//        tbRolePOQueryWrapper.eq("parent_id", tbRolePO.getId()).ne("id",0);
//        List<TbRolePO> tbRolePOs = baseMapper.selectList(tbRolePOQueryWrapper);
//        if (!Objects.isNull(tbRolePOs) && !tbRolePOs.isEmpty()) {
//            tbRolePOs.forEach(this::findChildren);
//        } else {
//            tbRolePOs = null;
//        }
//        tbRolePO.setChildren(tbRolePOs);
//    }


    @Override
    public List<String> findAllRoleNames() {
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        List<TbRolePO> roles = baseMapper.selectList(tbRolePOQueryWrapper.select("name"));

        Assert.notEmpty(roles, HttpStatus.NOT_FOUND.toString());

        return roles.stream().map(TbRolePO::getName).collect(Collectors.toList());
    }

    @Override
    public TbRolePO findRoleByName(String rolename) {
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        tbRolePOQueryWrapper.eq("name", rolename);
        TbRolePO tbRolePO = baseMapper.selectOne(tbRolePOQueryWrapper);

        Assert.notNull(tbRolePO, HttpStatus.NOT_FOUND.toString());

        return tbRolePO;
    }

    @Override
    public TbRolePO findRoleById(Integer id) {
        TbRolePO tbRolePO = baseMapper.selectById(id);

        Assert.notNull(tbRolePO, HttpStatus.NOT_FOUND.toString());

        return tbRolePO;
    }


    @Override
    public List<TbRolePO> findRolesByParentId(Integer parentId) {
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        tbRolePOQueryWrapper.eq("parent_id", parentId);
        List<TbRolePO> TbRolePOs = baseMapper.selectList(tbRolePOQueryWrapper);

        Assert.notEmpty(TbRolePOs, HttpStatus.NOT_FOUND.toString());

        return TbRolePOs;
    }

    @Override
    public List<String> findRoleNamesByParentId(Integer parentId) {
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        tbRolePOQueryWrapper.eq("parent_id", parentId);
        List<TbRolePO> tbRolePOS = baseMapper.selectList(tbRolePOQueryWrapper);

        Assert.notEmpty(tbRolePOS, HttpStatus.NOT_FOUND.toString());

        return tbRolePOS.stream().map(TbRolePO::getName).collect(Collectors.toList());
    }

    @Override
    public List<TbRolePO> findRolesByFactor(SearchFactorVO searchFactorVO) {
        return null;
    }

    /**
     * 添加
     */
    @Override
    public void addRole(TbRolePO tbRolePO) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = Integer.parseInt(name);
        tbRolePO.setCreatorId(userId).setUpdaterId(userId);
        int insert = baseMapper.insert(tbRolePO);
        Assert.isTrue(insert > 0, "添加角色失败");
    }

    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    @Transactional
    public void addRole(TbRolePO tbRolePO, List<Integer> permissionIds) {
        // TODO 手动回滚，并抛出异常

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = Integer.parseInt(name);
        tbRolePO.setCreatorId(userId).setUpdaterId(userId);

        try {
            int insert = baseMapper.insert(tbRolePO);
            Assert.isTrue(insert > 0, "添加角色失败");

            rolePermissionService.addPermissions(tbRolePO.getId(), permissionIds);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            throw new IllegalArgumentException("添加角色失败(@Transactional异常)");
        }

    }

    /**
     * 更新
     */
    @Override
    public void updateRole(TbRolePO tbRolePO) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = Integer.parseInt(name);
        tbRolePO.setParentId(null).setUpdaterId(userId);
        int update = baseMapper.updateById(tbRolePO);
        Assert.isTrue(update > 0, "更新角色失败");
    }

    @Override
    @Transactional
    public void updateRole(TbRolePO tbRolePO, List<Integer> permissionId) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = Integer.parseInt(name);
        // 先更新角色表
        tbRolePO.setParentId(null).setUpdaterId(userId);
        int update = baseMapper.updateById(tbRolePO);
        Assert.isTrue(update > 0, "更新角色失败");
        // 再更新角色权限关系表。需要先把原来的关系删除，在添加新的关系。
        try {
            rolePermissionService.deletePermissionsByRoleId(tbRolePO.getId());
            rolePermissionService.addPermissions(tbRolePO.getId(), permissionId);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            throw new IllegalArgumentException("修改角色失败(@Transactional异常)");
        }
    }

    /**
     * 删除
     */
    @Override
    public void deleteRole(Integer roleId) {
        int delete = baseMapper.deleteById(roleId);
        Assert.isTrue(delete > 0, "删除角色失败");
    }
}
