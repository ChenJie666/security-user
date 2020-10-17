package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.po.TbRolePermissionPO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 14:16
 */
public interface RolePermissionService extends IService<TbRolePermissionPO> {

    List<Map<String, Object>> getPermissions(Integer roleId);

    void addPermissions(Integer roleId, List<Integer> permissionIds);

    void deletePermissions(List<Integer> bindIds);

    void deletePermissionsByRoleId(Integer roleId);
}
