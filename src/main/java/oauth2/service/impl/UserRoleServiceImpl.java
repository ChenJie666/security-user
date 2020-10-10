package oauth2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.UserRoleMapper;
import oauth2.entities.po.TbUserRolePO;
import oauth2.service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 14:16
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, TbUserRolePO> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Map<String, Object>> getRoles(Integer administratorId) {
        List<Map<String, Object>> roles = userRoleMapper.getRoles(administratorId);
        Assert.notEmpty(roles, HttpStatus.NOT_FOUND.toString());
        return roles;
    }

    @Override
    public void addRoles(Integer administratorId, List<Integer> roleIds) {
        List<TbUserRolePO> tbUserRolePOs = roleIds.stream().map(roleId -> {
            return new TbUserRolePO(null, administratorId, roleId);
        }).collect(Collectors.toList());
        // 批量插入
        boolean insert = saveBatch(tbUserRolePOs);
        Assert.isTrue(insert, "用户添加角色失败");
    }

    @Override
    public void deleteRoles(List<Integer> bindIds) {
        int delete = baseMapper.deleteBatchIds(bindIds);
        Assert.isTrue(delete != 0, "用户删除角色失败");
    }

}
