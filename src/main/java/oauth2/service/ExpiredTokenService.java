package oauth2.service;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 17:19
 */
public interface ExpiredTokenService {

    /**
     * 通过权限获取用户id
     * @return
     */
    List<Integer> getUserIdsByPermissions(List<Integer> permissionIds);

    /**
     * 通过角色获取用户id
     * @return
     */
    List<Integer> getUserIdsByRoles(List<Integer> roleIds);

    /**
     * 通过用户角色中间表获取用户id
     * @return
     */
    List<Integer> getUserIdsByUserRole(List<Integer> bindIds);

    /**
     * 通过角色权限中间表获取用户id
     * @return
     */
    List<Integer> getUserIdsByRolePermission(List<Integer> bindIds);

}
