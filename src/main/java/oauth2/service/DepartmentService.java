package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.po.DepartmentPO;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/16 13:58
 */
public interface DepartmentService extends IService<DepartmentPO> {

    List<DepartmentPO> findAllDepartments(Integer userId);

    Set<Integer> findBranchDepartmentIdsByUserId(int userid);

    void addDepartment(DepartmentPO departmentPO);

    void updateDepartment(DepartmentPO departmentPO);

    void changeParentId(Integer departmentId,Integer parentId);

    void deleteDepartment(Integer departmentId);

}
