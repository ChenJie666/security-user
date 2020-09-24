package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.*;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 15:01
 */
public interface PermissionService extends IService<TbPermissionPO> {

    /**
     * 查询所有权限信息
     * @return
     */
    ObjListPO<TbPermissionPO> findAllPermissions(Integer pageCurrent, Integer pageSize);

    /**
     * 查询所有权限名
     * @return
     */
    List<String> findAllPermissionNames();

    /**
     * 根据权限名查询权限信息
     * @return
     */
    TbPermissionPO findPermissionByName(String permissionname);

    /**
     * 根据权限id查询权限信息
     * @return
     */
    TbPermissionPO findPermissionById(Integer id);

    /**
     * 根据权限父id查询所有子权限信息
     * @return
     */
    List<TbPermissionPO> findPermissionsByParentId(Integer parentId);

    /**
     * 根据权限父id查询所有子权限名
     * @return
     */
    List<String> findPermissionNamesByParentId(Integer parentId);

    /**
     * 获取筛选后的所有权限信息
     * @param searchFactorVO
     * @return
     */
    List<TbPermissionPO> findPermissionsByFactor(SearchFactorVO searchFactorVO);
}
