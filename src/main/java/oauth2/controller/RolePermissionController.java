package oauth2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.utils.CommonResult;
import oauth2.service.ExpiredTokenService;
import oauth2.service.RolePermissionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 14:13
 */
@RestController
@Api(value = "角色绑定权限", tags = {"角色绑定权限"})
public class RolePermissionController {

    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 角色绑定权限查询
     */
    @GetMapping(path = "/uc/binding/getPermissions/{roleId}")
    @ApiOperation(value = "角色绑定权限查询")
    public CommonResult<List<Map<String, Object>>> getPermissions(@ApiParam(name = "roleId", value = "角色id", required = true) @PathVariable Integer roleId) {
        List<Map<String, Object>> permissions = rolePermissionService.getPermissions(roleId);

        return CommonResult.success(permissions);
    }

    @Resource
    private ExpiredTokenService expiredTokenService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 角色绑定权限增加(添加到过期名单中)
     */
    @PostMapping(path = "/uc/binding/addPermissions/{roleId}")
    @ApiOperation(value = "角色绑定权限增加")
    public CommonResult<String> addPermissions(@ApiParam(name = "roleId", value = "角色id", required = true)
                                               @PathVariable Integer roleId,
                                               @ApiParam(name = "permissionIds", value = "权限id")
                                               @RequestBody List<Integer> permissionIds) {
        rolePermissionService.addPermissions(roleId, permissionIds);

        // 异步添加到过期列表
        new Thread(new FutureTask<>(() -> {
            List<Integer> userIds = expiredTokenService.getUserIdsByRoles(Collections.singletonList(roleId));
            userIds.forEach((administratorId) -> {
                rabbitTemplate.convertAndSend("ExpiredTokenExchange", "expiredToken.addExpiredToken", Arrays.asList(administratorId, System.currentTimeMillis() / 1000));
            });
            return 0;
        })).start();

        return CommonResult.success("角色绑定权限成功");
    }

    /**
     * 角色绑定权限删除(添加到过期名单中)
     */
    @DeleteMapping(path = "/uc/binding/deletePermissions")
    @ApiOperation(value = "角色绑定权限删除")
    public CommonResult<String> deletePermissions(@ApiParam(name = "bindIds", value = "角色权限中间表的id列表", required = true)
                                                  @RequestBody List<Integer> bindIds) {
        rolePermissionService.deletePermissions(bindIds);

        // 异步添加到过期列表
        new Thread(new FutureTask<>(() -> {
            List<Integer> userIds = expiredTokenService.getUserIdsByRolePermission(bindIds);
            userIds.forEach((administratorId) -> {
                rabbitTemplate.convertAndSend("ExpiredTokenExchange", "expiredToken.addExpiredToken", Arrays.asList(administratorId, System.currentTimeMillis() / 1000));
            });
            return 0;
        })).start();

        return CommonResult.success("角色删除权限成功");
    }

}
