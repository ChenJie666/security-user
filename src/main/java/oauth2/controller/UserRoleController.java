package oauth2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.utils.CommonResult;
import oauth2.service.ExpiredTokenService;
import oauth2.service.UserRoleService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 14:13
 */
@RestController
@Api(value = "用户绑定角色", tags = {"用户绑定角色"})
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    /**
     * 用户绑定角色信息查询
     */
    @GetMapping(path = "/uc/binding/getRoles/{administratorId}")
    @ApiOperation(value = "用户绑定角色信息查询")
    public CommonResult<List<Map<String, Object>>> getRoles(@ApiParam(name = "administratorId", value = "管理员id", required = true) @PathVariable Integer administratorId) {
        List<Map<String, Object>> roles = userRoleService.getRoles(administratorId);

        return CommonResult.success(roles);
    }

    @Resource
    private ExpiredTokenService expiredTokenService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 用户绑定角色增加(添加到过期名单中)
     */
    @PostMapping(path = "/uc/binding/addRoles/{administratorId}")
    @ApiOperation(value = "用户绑定角色增加")
    public CommonResult<String> addRoles(@ApiParam(name = "administratorId", value = "管理员id", required = true)
                                         @PathVariable Integer administratorId,
                                         @ApiParam(name = "roleIds", value = "角色id")
                                         @RequestBody List<Integer> roleIds) {
        userRoleService.addRoles(administratorId, roleIds);

        // 异步添加到过期列表
        new Thread(new FutureTask<>(() -> {
            rabbitTemplate.convertAndSend("ExpiredTokenExchange", "expiredToken.addExpiredToken", Arrays.asList(administratorId, System.currentTimeMillis() / 1000));
            return 0;
        })).start();

        return CommonResult.success("用户绑定角色成功");
    }

    /**
     * 用户绑定角色删除(添加到过期名单中)
     */
    @DeleteMapping(path = "/uc/binding/deleteRoles")
    @ApiOperation(value = "用户绑定角色删除")
    public CommonResult<String> deleteRoles(@ApiParam(name = "bindIds", value = "用户角色中间表的id列表", required = true)
                                            @RequestBody List<Integer> bindIds) {
        userRoleService.deleteRoles(bindIds);

        // 异步添加到过期列表
        new Thread(new FutureTask<>(() -> {
            List<Integer> userIds = expiredTokenService.getUserIdsByUserRole(bindIds);
            userIds.forEach((administratorId) -> {
                rabbitTemplate.convertAndSend("ExpiredTokenExchange", "expiredToken.addExpiredToken", Arrays.asList(administratorId, System.currentTimeMillis() / 1000));
            });
            return 0;
        })).start();

        return CommonResult.success("用户绑定角色删除成功");
    }

}
