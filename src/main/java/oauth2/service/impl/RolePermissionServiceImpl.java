package oauth2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.RolePermissionMapper;
import oauth2.entities.po.TbRolePermissionPO;
import oauth2.service.RolePermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 14:17
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, TbRolePermissionPO> implements RolePermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Map<String, Object>> getPermissions(Integer roleId) {
        List<Map<String, Object>> permissions = rolePermissionMapper.getPermissions(roleId);
        Assert.notEmpty(permissions, HttpStatus.NOT_FOUND.toString());
        return permissions;
    }

    @Override
    public void addPermissions(Integer roleId, List<Integer> permissionIds) {
        List<TbRolePermissionPO> tbRolePermissionPOs = permissionIds.stream().map(permissionId -> {
            return new TbRolePermissionPO(null,roleId,permissionId);
        }).collect(Collectors.toList());
        // 批量插入
        boolean insert = saveBatch(tbRolePermissionPOs);
        Assert.isTrue(insert, "角色添加权限失败");
    }

    @Override
    public void deletePermissions(List<Integer> bindIds) {
        int delete = baseMapper.deleteBatchIds(bindIds);
        Assert.isTrue(delete != 0, "角色删除权限失败");
    }

}
