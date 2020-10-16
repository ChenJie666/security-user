package oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.AdministratorMapper;
import oauth2.dao.DepartmentMapper;
import oauth2.entities.po.DepartmentPO;
import oauth2.entities.po.TbUserPO;
import oauth2.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
     * @return
     */
    @Override
    public DepartmentPO findAllDepartments(Integer userId) {
        QueryWrapper<DepartmentPO> departmentPOQueryWrapper = new QueryWrapper<>();
        departmentPOQueryWrapper.eq("parent_id", 0);
        DepartmentPO departmentPO = departmentMapper.selectOne(departmentPOQueryWrapper);

        String myLevel = departmentMapper.findLevelByUserId(userId);

        findChildren(departmentPO,myLevel);

        return null;
    }

    private void findChildren(DepartmentPO departmentPO,String myLevel){
        // 判断是否是分支
        String level = departmentPO.getLevel();
        boolean isBranch = level.startsWith(myLevel) && level.length() > myLevel.length();
        // 设置
        departmentPO.setIsBranch(isBranch);

        // 查询部门下所有的用户，设置isBranch，添加到departmentPO中
        Integer departmentId = departmentPO.getId();
        List<TbUserPO> tbUserPOS = administratorMapper.selectByMap(Collections.singletonMap("parent_id", departmentId));
        tbUserPOS.forEach(tbUserPO -> tbUserPO.setIsBranch(isBranch));
        departmentPO.setTbUserPOs(tbUserPOS);

        System.out.println("*****findChildren:" + departmentPO);
        QueryWrapper<DepartmentPO> tbDepartmentPOQueryWrapper = new QueryWrapper<>();
        tbDepartmentPOQueryWrapper.eq("parent_id", departmentPO.getId()).ne("id",0);
        List<DepartmentPO> departmentPOs = baseMapper.selectList(tbDepartmentPOQueryWrapper);
        if (!Objects.isNull(departmentPOs) && !departmentPOs.isEmpty()) {
            departmentPOs.forEach(department -> {
                findChildren(department,myLevel);
            });
        } else {
            departmentPOs = null;
        }

        // 查询创建更新者的用户名
        if (!Objects.isNull(departmentPO.getCreatorId())) {
            String creator = administratorMapper.getUsernameById(departmentPO.getCreatorId());
            departmentPO.setCreatorName(creator);

            if(!Objects.isNull(departmentPO.getUpdaterId())) {
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
     * 查询所有该用户的所有子部门
     * @param userId
     * @return
     */
    @Override
    public Set<Integer> findBranchDepartmentIdsByUserId(int userId) {
        // 获取用户所在的部门的level
        String level = departmentMapper.findLevelByUserId(userId);

        // 通过该level查询其所有的子部门和员工
        QueryWrapper<DepartmentPO> departmentPOQueryWrapper = new QueryWrapper<>();
        departmentPOQueryWrapper.ne("level",level).likeRight("level", level);
        List<DepartmentPO> departmentPOs = baseMapper.selectList(departmentPOQueryWrapper);

        return departmentPOs.stream().map(DepartmentPO::getId).collect(Collectors.toSet());
    }

    @Override
    public void addDepartment(DepartmentPO departmentPO) {
        // TODO
    }

    /**
     * 修改部门信息，不能修改部门的级属关系
     * @param departmentPO
     */
    @Override
    public void updateDepartment(DepartmentPO departmentPO) {
        // TODO
    }

    /**
     * 修改部门的级属关系
     */
    @Override
    @Transactional
    public void changeParentId(Integer departmentId,Integer parentId) {
        // TODO
        // 获取新的level
        DepartmentPO departmentPO = baseMapper.selectById(departmentId);
        String oldLevel = departmentPO.getLevel();
        String newLevel = oldLevel.substring(0,oldLevel.lastIndexOf(".")) + "." + parentId;

        // 查询所有的子部门，并修改自己和子部门的parentId和level
        QueryWrapper<DepartmentPO> departmentPOQueryWrapper = new QueryWrapper<>();
        departmentPOQueryWrapper.likeRight("level", oldLevel);
        List<DepartmentPO> departmentPOs = baseMapper.selectList(departmentPOQueryWrapper);
        // TODO 对于stream流处理，事务还能生效吗？？？
        departmentPOs.forEach(department -> {
            String level = department.getLevel();
            level = newLevel + "." + level.substring(oldLevel.length());
            department.setParentId(parentId).setLevel(level);
            baseMapper.updateById(department);
        });

    }

    /**
     * 删除部门，如果该部门下有子部门或用户则删除失败
     * @param departmentId
     */
    @Override
    public void deleteDepartment(Integer departmentId) {
        // TODO
    }

}
