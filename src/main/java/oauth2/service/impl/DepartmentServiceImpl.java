package oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.AdministratorMapper;
import oauth2.dao.DepartmentMapper;
import oauth2.dao.RoleMapper;
import oauth2.entities.po.DepartmentPO;
import oauth2.entities.po.TbRolePO;
import oauth2.entities.po.TbUserPO;
import oauth2.service.DepartmentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/16 13:58
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, DepartmentPO> implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private AdministratorMapper administratorMapper;

    /**
     * 查询所有的部门信息和人员信息，并标识是否有权修改
     *
     * @return
     */
    @Override
    public List<DepartmentPO> findAllDepartments(Integer userId) {
        QueryWrapper<DepartmentPO> departmentPOQueryWrapper = new QueryWrapper<>();
        departmentPOQueryWrapper.eq("parent_id", 0);
        List<DepartmentPO> departmentPOS = departmentMapper.selectList(departmentPOQueryWrapper);

        String myLevel;
        if (userId != -1) {
            Map<String, Object> map = departmentMapper.findLevelByUserId(userId);
            int id = (Integer) map.get("id");
            myLevel = map.get("level") + "." + id;
        } else {
            myLevel = "-1";
        }

        departmentPOS.forEach(departmentPO -> {
            findChildren(departmentPO, myLevel, userId);
        });

        return departmentPOS;
    }

    @Resource
    private RoleMapper roleMapper;

    private void findChildren(DepartmentPO departmentPO, String myLevel, Integer userId) {
        // 判断是否是分支
        String level = departmentPO.getLevel() + "." + departmentPO.getId();
        System.out.println("***myLevel:" + myLevel);
        System.out.println("***level:" + level);
        boolean isBranch = level.startsWith(myLevel) && level.length() > myLevel.length();
        // 设置
        departmentPO.setIsBranch(isBranch);

        // 查询部门下所有的用户，设置isBranch，添加到departmentPO中
        Integer departmentId = departmentPO.getId();
        List<TbUserPO> tbUserPOS = administratorMapper.selectByMap(Collections.singletonMap("parent_id", departmentId));
        tbUserPOS.forEach(tbUserPO -> {
            tbUserPO.setIsBranch(isBranch).setPassword(null);
            if (tbUserPO.getId().equals(userId)) {
                tbUserPO.setIsBranch(true);
            }

            //查询用户的角色信息
            List<TbRolePO> rolesByUserId = roleMapper.findRolesByUserId(userId);
            tbUserPO.setRolePOs(rolesByUserId);

            //查询creator和updater
            if (!Objects.isNull(tbUserPO.getCreatorId())) {
                String creator = administratorMapper.getUsernameById(tbUserPO.getCreatorId());
                tbUserPO.setCreatorName(creator);

                if (!Objects.isNull(tbUserPO.getUpdaterId())) {
                    if (tbUserPO.getCreatorId().equals(tbUserPO.getUpdaterId())) {
                        tbUserPO.setUpdaterName(creator);
                    } else {
                        String updater = administratorMapper.getUsernameById(tbUserPO.getUpdaterId());
                        tbUserPO.setUpdaterName(updater);
                    }
                }
            }
        });
        departmentPO.setTbUserPOs(tbUserPOS);

        System.out.println("*****findChildren:" + departmentPO);
        QueryWrapper<DepartmentPO> tbDepartmentPOQueryWrapper = new QueryWrapper<>();
        tbDepartmentPOQueryWrapper.eq("parent_id", departmentPO.getId()).ne("id", 0);
        List<DepartmentPO> departmentPOs = baseMapper.selectList(tbDepartmentPOQueryWrapper);
        if (!Objects.isNull(departmentPOs) && !departmentPOs.isEmpty()) {
            departmentPOs.forEach(department -> {
                findChildren(department, myLevel, userId);
            });
        } else {
            departmentPOs = null;
        }

        // 查询创建更新者的用户名
        if (!Objects.isNull(departmentPO.getCreatorId())) {
            String creator = administratorMapper.getUsernameById(departmentPO.getCreatorId());
            departmentPO.setCreatorName(creator);

            if (!Objects.isNull(departmentPO.getUpdaterId())) {
                if (departmentPO.getCreatorId().equals(departmentPO.getUpdaterId())) {
                    departmentPO.setUpdaterName(creator);
                } else {
                    String updater = administratorMapper.getUsernameById(departmentPO.getUpdaterId());
                    departmentPO.setUpdaterName(updater);
                }
            }
        }

        departmentPO.setDepartmentPOs(departmentPOs);
    }


    /**
     * 查询该用户的所有子部门
     *
     * @param userId
     * @return
     */
    @Override
    public Set<Integer> findBranchDepartmentIdsByUserId(int userId) {
        // 获取用户所在的部门的level
        Map<String, Object> map = departmentMapper.findLevelByUserId(userId);
        int id = (Integer) map.get("id");
        String level = (String) map.get("level");
        System.out.println("*****findBranchDepartmentIdsByUserId: " + id + "---" + level);

        // 通过该level查询其所有的子部门和员工
        QueryWrapper<DepartmentPO> departmentPOQueryWrapper = new QueryWrapper<>();
        departmentPOQueryWrapper.ne("level", level).likeRight("level", level);
        List<DepartmentPO> departmentPOs = baseMapper.selectList(departmentPOQueryWrapper);

        return departmentPOs.stream().map(DepartmentPO::getId).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void addDepartment(DepartmentPO departmentPO) {
        // 获取当前用户用于添加创建更新者
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer userId = Integer.parseInt(username);

        Integer parentId = departmentPO.getParentId();
        String level = baseMapper.selectById(parentId).getLevel();
        // 设置level，创建更新者
        departmentPO.setLevel(level + "." + parentId).setCreatorId(userId).setUpdaterId(userId);

        int insert = baseMapper.insert(departmentPO);
        Assert.isTrue(insert > 0, "部门创建失败");
    }

    /**
     * 修改部门信息，不能修改部门的级属关系
     *
     * @param departmentPO
     */
    @Override
    public void updateDepartment(DepartmentPO departmentPO) {
        // 获取当前用户用于添加创建更新者
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer userId = Integer.parseInt(username);
        // 不能修改parentId和level，设置更新者
        departmentPO.setParentId(null).setLevel(null).setCreatorId(null).setUpdaterId(userId);

        int update = baseMapper.updateById(departmentPO);
        Assert.isTrue(update > 0, "部门更新失败");
    }

    /**
     * 修改部门的级属关系
     */
    @Override
    @Transactional
    public void changeParentId(Integer departmentId, Integer parentId) {
        // 获取新的level
        DepartmentPO departmentPO = baseMapper.selectById(departmentId);
        String oldLevel = departmentPO.getLevel();
        DepartmentPO parentDepratmentPO = baseMapper.selectById(parentId);
        String newLevel = parentDepratmentPO.getLevel() + "." + parentId;

//        DepartmentPO departmentPO = baseMapper.selectById(departmentId);
//        String oldLevel = departmentPO.getLevel();
//        String parentLevel = oldLevel.substring(0, oldLevel.lastIndexOf("."));
//        DepartmentPO parentDepartmentPO = baseMapper.selectById(parentId);
//        String newLevel = parentDepartmentPO.getLevel();

        // 查询所有的子部门，并修改自己和子部门的parentId和level
        QueryWrapper<DepartmentPO> departmentPOQueryWrapper = new QueryWrapper<>();
        departmentPOQueryWrapper.likeRight("level", oldLevel);
        List<DepartmentPO> departmentPOs = baseMapper.selectList(departmentPOQueryWrapper);

        try {
            // TODO 对于stream流处理，事务还能生效吗？？？
            departmentPOs.forEach(department -> {
                String level = department.getLevel();
                level = newLevel + level.substring(oldLevel.length());
                department.setLevel(level);
                if(department.getId() == departmentId){
                    department.setParentId(parentId);
                }
                int update = baseMapper.updateById(department);
                Assert.isTrue(update > 0, "添加角色失败");
            });
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            throw new IllegalArgumentException("改变层级失败(@Transactional异常)");
        }

    }

    /**
     * 删除部门，如果该部门下有子部门或用户则删除失败
     *
     * @param departmentId
     */
    @Override
    public void deleteDepartment(Integer departmentId) {
        // 查询是否有子用户
        List<TbUserPO> tbUserPOS = administratorMapper.selectByMap(Collections.singletonMap("parent_id", departmentId));

        // 查询是否有子部门
        List<DepartmentPO> departmentPOS = baseMapper.selectByMap(Collections.singletonMap("parent_id", departmentId));

        System.out.println("***tbUserPOS:" + tbUserPOS + "--" + "***departmentPOS:" + departmentPOS);

        Assert.isTrue(tbUserPOS.isEmpty(), "请先删除子目录或用户");
        Assert.isTrue(departmentPOS.isEmpty(), "请先删除子目录或用户");

        int delete = baseMapper.deleteById(departmentId);
        Assert.isTrue(delete > 0, "删除部门失败");
    }

}
