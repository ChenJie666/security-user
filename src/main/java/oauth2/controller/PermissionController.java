package oauth2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.entities.*;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.po.TbPermissionPO;
import oauth2.service.ExpiredTokenService;
import oauth2.service.PermissionService;
import oauth2.utils.CommonResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 16:20
 */
@RestController
@Api(value = "权限操作API", tags = {"权限信息"})
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 查询
     */
//    @GetMapping(path = "/uc/permission/findAllPermissions")
//    @ApiOperation(value = "查询所有的权限")
//    public CommonResult<ObjListPO<TbPermissionPO>> findAllPermissions(@ApiParam(name = "page", value = "查询的页数", required = true)
//                                                                      @PathVariable Integer page,
//                                                                      @ApiParam(name = "pageSize", value = "每页的记录数", required = true)
//                                                                      @PathVariable Integer pageSize) {
//        return CommonResult.success(permissionService.findAllPermissions(page, pageSize));
//    }
    @GetMapping(path = "/uc/permission/findAllPermissions")
    @ApiOperation(value = "查询所有的权限")
    public CommonResult<TbPermissionPO> findAllPermissions(@ApiParam(name = "permissionId", value = "权限id", required = true)
                                                           @RequestParam Integer permissionId) {
        return CommonResult.success(permissionService.findAllPermissions(permissionId));
    }

    @GetMapping(path = "/uc/permission/findAllPermissionNames")
    @ApiOperation(value = "查询所有的权限名")
    public CommonResult<List<String>> findAllPermissionNames() {
        return CommonResult.success(permissionService.findAllPermissionNames());
    }

    @GetMapping(path = "/uc/permission/findPermissionByName/{permissionname}")
    @ApiOperation(value = "根据权限名查询权限")
    public CommonResult<TbPermissionPO> findPermissionByName(@ApiParam(name = "permissionname", value = "权限名", required = true) @PathVariable String permissionname) {
        return CommonResult.success(permissionService.findPermissionByName(permissionname));
    }

    @GetMapping(path = "/uc/permission/findPermissionById/{id}")
    @ApiOperation(value = "根据权限id查询权限")
    public CommonResult<TbPermissionPO> findPermissionById(@ApiParam(name = "id", value = "权限id", required = true) @PathVariable Integer id) {
        return CommonResult.success(permissionService.findPermissionById(id));
    }

    @GetMapping(path = "/uc/permission/findPermissionsByParentId/{parentId}")
    @ApiOperation(value = "根据权限父id查询所有权限")
    public CommonResult<List<TbPermissionPO>> findPermissionsByParentId(@ApiParam(name = "parentId", value = "父id", required = true) @PathVariable Integer parentId) {
        return CommonResult.success(permissionService.findPermissionsByParentId(parentId));
    }

    @GetMapping(path = "/uc/permission/findPermissionNamesByParentId/{parentId}")
    @ApiOperation(value = "根据权限父id查询所有权限名")
    public CommonResult<List<String>> findPermissionNamesByParentId(@ApiParam(name = "parentId", value = "父id", required = true) @PathVariable Integer parentId) {
        return CommonResult.success(permissionService.findPermissionNamesByParentId(parentId));
    }

    @PostMapping(path = "/uc/permission/findPermissionsByFactor")
    @ApiOperation(value = "根据条件查询权限")
    public CommonResult<List<TbPermissionPO>> findPermissionsByFactor(@ApiParam(name = "searchFactorVO", value = "筛选信息", required = true) @RequestBody SearchFactorVO searchFactorVO) {
        return CommonResult.success(permissionService.findPermissionsByFactor(searchFactorVO));
    }

    /**
     * 添加
     */
    @PostMapping(path = "/uc/permission/addPermission")
    @ApiOperation(value = "添加权限")
    public CommonResult<String> addPermission(@ApiParam(name = "tbPermissionPO", value = "权限对象", required = true) @RequestBody TbPermissionPO tbPermissionPO) {
        System.out.println("*****tbPermissionPO:" + tbPermissionPO);
        permissionService.addPermission(tbPermissionPO);
        return CommonResult.success("权限添加成功");
    }

    @Resource
    private ExpiredTokenService expiredTokenService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 修改(添加有该角色的用户到过期列表)
     */
    @PostMapping(path = "/uc/permission/updatePermission")
    @ApiOperation(value = "更新权限")
    public CommonResult<String> updatePermission(@ApiParam(name = "TbPermissionPO", value = "权限对象", required = true) @RequestBody TbPermissionPO tbPermissionPO) {
        System.out.println("*****tbPermissionPO:" + tbPermissionPO);
        permissionService.updatePermission(tbPermissionPO);

        // 异步添加到过期列表
        new Thread(new FutureTask<>(() -> {
            List<Integer> userIds = expiredTokenService.getUserIdsByPermissions(Collections.singletonList(tbPermissionPO.getId()));
            userIds.forEach((administratorId) -> {
                rabbitTemplate.convertAndSend("ExpiredTokenExchange", "expiredToken.addExpiredToken", Arrays.asList(administratorId, System.currentTimeMillis() / 1000));
            });
            return 0;
        })).start();

        return CommonResult.success("权限修改成功");
    }

    /**
     * 删除(添加有该角色的用户到过期列表)
     */
    @DeleteMapping(path = "/uc/permission/deletePermission/{permissionId}")
    @ApiOperation(value = "删除权限")
    public CommonResult<String> deletePermission(@ApiParam(name = "permissionId", value = "权限id", required = true) @PathVariable Integer permissionId) {
        permissionService.deletePermission(permissionId);

        // 异步添加到过期列表
        new Thread(new FutureTask<>(() -> {
            List<Integer> userIds = expiredTokenService.getUserIdsByPermissions(Collections.singletonList(permissionId));
            userIds.forEach((administratorId) -> {
                rabbitTemplate.convertAndSend("ExpiredTokenExchange", "expiredToken.addExpiredToken", Arrays.asList(administratorId, System.currentTimeMillis() / 1000));
            });
            return 0;
        })).start();

        return CommonResult.success("权限删除成功");
    }

}
