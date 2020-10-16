package oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.AdministratorMapper;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.po.TbUserPO;
import oauth2.service.AdministratorService;
import oauth2.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 10:27
 */
@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, TbUserPO> implements AdministratorService {

//    @Override
//    public TbUserPO findMyUsers(Integer userId) {
//        TbUserPO tbUserPO = baseMapper.selectById(userId);
//        Assert.notNull(tbUserPO, HttpStatus.NOT_FOUND.toString());
//
//        findChildren(tbUserPO);
//
//        return tbUserPO;
//    }

    @Resource
    private DepartmentService departmentService;

    @Override
    public Set<Integer> findBranchUserIdsByUserId(Integer userId) {
        Set<Integer> departmentIds = departmentService.findBranchDepartmentIdsByUserId(userId);

        // 查找所有子用户的id
        QueryWrapper<TbUserPO> tbUserPOQueryWrapper = new QueryWrapper<>();
        tbUserPOQueryWrapper.in("parent_id", departmentIds);
        List<TbUserPO> tbUserPOS = baseMapper.selectList(tbUserPOQueryWrapper);

        return tbUserPOS.stream().map(TbUserPO::getId).collect(Collectors.toSet());
    }

//    @Resource
//    private AdministratorMapper administratorMapper;

//    private void findChildren(TbUserPO tbUserPO) {
//        tbUserPO.setPassword(null);
//        System.out.println("*****findChildren:" + tbUserPO);
//        QueryWrapper<TbUserPO> tbRolePOQueryWrapper = new QueryWrapper<>();
//        tbRolePOQueryWrapper.eq("parent_id", tbUserPO.getId()).ne("id", 0);
//        List<TbUserPO> tbRolePOs = baseMapper.selectList(tbRolePOQueryWrapper);
//        if (!Objects.isNull(tbRolePOs) && !tbRolePOs.isEmpty()) {
//            tbRolePOs.forEach(this::findChildren);
//        } else {
//            tbRolePOs = null;
//        }
//
//        // 查询创建更新者的用户名
//        if (!Objects.isNull(tbUserPO.getCreatorId())) {
//            String creator = administratorMapper.getUsernameById(tbUserPO.getCreatorId());
//            tbUserPO.setCreatorName(creator);
//
//            if(!Objects.isNull(tbUserPO.getUpdaterId())) {
//                if (tbUserPO.getCreatorId().equals(tbUserPO.getUpdaterId())) {
//                    tbUserPO.setUpdaterName(creator);
//                } else {
//                    String updater = administratorMapper.getUsernameById(tbUserPO.getUpdaterId());
//                    tbUserPO.setUpdaterName(updater);
//                }
//            }
//        }
//
//        tbUserPO.setChildren(tbRolePOs);
//    }

//    private void findChildren(TbUserPO tbUserPO, Set<Integer> userIds) {
//        System.out.println("*****findChildren:" + tbUserPO);
//        QueryWrapper<TbUserPO> tbRolePOQueryWrapper = new QueryWrapper<>();
//        tbRolePOQueryWrapper.eq("parent_id", tbUserPO.getId()).ne("id", 0);
//        List<TbUserPO> tbUserPOs = baseMapper.selectList(tbRolePOQueryWrapper);
//        if (!Objects.isNull(tbUserPOs) && !tbUserPOs.isEmpty()) {
//            tbUserPOs.forEach(user -> this.findChildren(user, userIds));
//        } else {
//            tbUserPOs = null;
//        }
//        tbUserPO.setChildren(tbUserPOs);
//        userIds.add(tbUserPO.getId());
//    }

//    @Override
//    public List<String> findAllUserNames() {
//        QueryWrapper<TbUserPO> tbUserPOQueryWrapper = new QueryWrapper<>();
//        List<TbUserPO> users = baseMapper.selectList(tbUserPOQueryWrapper.select("username"));
//
//        Assert.notEmpty(users, HttpStatus.NOT_FOUND.toString());
//
//        return users.stream().map(TbUserPO::getUsername).collect(Collectors.toList());
//    }
//
//    @Override
//    public TbUserPO findUserByName(String username) {
//        QueryWrapper<TbUserPO> tbUserPOQueryWrapper = new QueryWrapper<>();
//        tbUserPOQueryWrapper.eq("username", username);
//        TbUserPO tbUserPO = baseMapper.selectOne(tbUserPOQueryWrapper);
//
//        Assert.notNull(tbUserPO, HttpStatus.NOT_FOUND.toString());
//
//        return tbUserPO;
//    }

    @Override
    public TbUserPO findUserById(Integer id) {
        TbUserPO tbUserPO = baseMapper.selectById(id);

        Assert.notNull(tbUserPO, HttpStatus.NOT_FOUND.toString());

        return tbUserPO;
    }

//    @Override
//    public List<TbUserPO> findUsersByFactor(SearchFactorVO searchFactorVO) {
//        // TODO
//
//        return null;
//    }

    /**
     * 添加
     */
    @Override
    public void addAdministrator(TbUserPO tbAdministratorPO) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = Integer.parseInt(name);
        tbAdministratorPO.setCreatorId(userId).setUpdaterId(userId);
        int insert = baseMapper.insert(tbAdministratorPO);
        Assert.isTrue(insert > 0, "添加管理员失败");
    }

    /**
     * 更新
     */
    @Override
    public void updateAdministrator(TbUserPO tbAdministratorPO) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = Integer.parseInt(name);
        tbAdministratorPO.setUpdaterId(userId);
        //不能修改用户的密码，有效性和层级关系
        tbAdministratorPO.setPassword(null).setParentId(null).setAccountNonExpired(null).setAccountNonLocked(null).setCredentialsNonExpired(null).setEnabled(null);
        int update = baseMapper.updateById(tbAdministratorPO);
        Assert.isTrue(update > 0, "更新管理员失败");
    }

    @Override
    public void enableAdministrator(Integer userId) {
        TbUserPO tbUserPO = baseMapper.selectById(userId);
        Boolean enabled = tbUserPO.getEnabled();
        int update = baseMapper.updateById(new TbUserPO().setId(userId).setEnabled(!enabled));
        Assert.isTrue(update > 0, "管理员有效性更改失败");
    }

    @Override
    public void changeParentId(Integer userId,Integer parentId) {
        int update = baseMapper.updateById(new TbUserPO().setParentId(parentId));
        Assert.isTrue(update > 0, "管理员部门修改失败");
    }

    /**
     * 删除
     */
    @Override
    public void deleteAdministrator(Integer administratorId) {
        int delete = baseMapper.deleteById(administratorId);
        Assert.isTrue(delete > 0, "删除管理员失败");
    }

}
