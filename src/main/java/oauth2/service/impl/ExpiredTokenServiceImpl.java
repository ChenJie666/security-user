package oauth2.service.impl;

import oauth2.dao.ExpiredTokenMapper;
import oauth2.service.ExpiredTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 17:19
 */
@Service
public class ExpiredTokenServiceImpl implements ExpiredTokenService {

    @Resource
    private ExpiredTokenMapper expiredTokenMapper;

    @Override
    public List<Integer> getUserIdsByPermissions(List<Integer> permissionIds) {
        List<Integer> roleIds = expiredTokenMapper.getRoleIdsByPermissionIds(permissionIds);
        return expiredTokenMapper.getUserIdsByRoleIds(roleIds);
    }

    @Override
    public List<Integer> getUserIdsByRoles(List<Integer> roleIds) {
        return expiredTokenMapper.getUserIdsByRoleIds(roleIds);
    }

    @Override
    public List<Integer> getUserIdsByUserRole(List<Integer> bindIds) {
        return expiredTokenMapper.getUserIdsByUserRoleIds(bindIds);
    }

    @Override
    public List<Integer> getUserIdsByRolePermission(List<Integer> bindIds) {
        List<Integer> roleIds = expiredTokenMapper.getRoleIdsByRolePermissionIds(bindIds);
        return expiredTokenMapper.getUserIdsByRoleIds(roleIds);
    }
}
