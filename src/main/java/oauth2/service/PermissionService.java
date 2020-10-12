package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.*;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.po.TbPermissionPO;

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

    TbPermissionPO findAllPermissions(Integer permissionId);

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

    /**
     * 添加权限
     * @param tbPermissionPO
     * @return
     */
    void addPermission(TbPermissionPO tbPermissionPO);

    /**
     * 更改权限
     * @param tbPermissionPO
     * @return
     */
    void updatePermission(TbPermissionPO tbPermissionPO);

    /**
     * 删除权限
     * @param permissionId
     * @return
     */
    void deletePermission(Integer permissionId);

}
