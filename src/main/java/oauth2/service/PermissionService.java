package oauth2.service;

import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbPermissionPO;
import oauth2.entities.TbUserPO;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 15:01
 */
public interface PermissionService {

    /**
     * 查询所有权限信息
     * @return
     */
    List<TbPermissionPO> findAllPermissions();

    /**
     * 查询所有权限名
     * @return
     */
    List<String> findAllPermissionNames();

    /**
     * 根据权限名查询权限信息
     * @return
     */
    TbPermissionPO findPermissionByName();

    /**
     * 根据权限id查询权限信息
     * @return
     */
    TbPermissionPO findPermissionById();

    /**
     * 获取筛选后的所有权限信息
     * @param searchFactorVO
     * @return
     */
    List<TbPermissionPO> findPermissionsByFactor(SearchFactorVO searchFactorVO);
}
