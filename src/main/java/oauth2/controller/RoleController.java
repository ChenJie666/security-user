package oauth2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.entities.po.TbRolePO;
import oauth2.service.ExpiredTokenService;
import oauth2.service.RoleService;
import oauth2.utils.CommonResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Api(value = "角色操作API", tags = {"角色信息"})
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 查询
     */
//    @GetMapping(path = "/uc/role/findAllRoles")
//    @ApiOperation(value = "查询所有的角色")
//    public CommonResult<ObjListPO<TbRolePO>> findAllRoles(@ApiParam(name = "page", value = "查询的页数", required = true)
//                                                          @RequestParam Integer page,
//                                                          @ApiParam(name = "pageSize", value = "每页的记录数", required = true)
//                                                          @RequestParam Integer pageSize) {
//        return CommonResult.success(roleService.findAllRoles(page, pageSize));
//    }
    @GetMapping(path = "/uc/role/findMyRoles")
    @ApiOperation(value = "查询该用户和其子用户所拥有角色")
    public CommonResult<List<TbRolePO>> findMyRoles() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return CommonResult.success(roleService.findMyRoles(Integer.parseInt(userId)));
    }

//    @GetMapping(path = "/uc/role/findAllRoleNames")
//    @ApiOperation(value = "查询所有的角色名")
//    public CommonResult<List<String>> findAllRoleNames() {
//        return CommonResult.success(roleService.findAllRoleNames());
//    }

//    @GetMapping(path = "/uc/role/findRoleByName/{rolename}")
//    @ApiOperation(value = "根据角色名查询角色")
//    public CommonResult<TbRolePO> findRoleByName(@ApiParam(name = "rolename", value = "角色名", required = true) @PathVariable String rolename) {
//        return CommonResult.success(roleService.findRoleByName(rolename));
//    }

    @GetMapping(path = "/uc/role/findRoleById/{id}")
    @ApiOperation(value = "根据角色id查询角色")
    public CommonResult<TbRolePO> findRoleById(@ApiParam(name = "id", value = "角色id", required = true) @PathVariable Integer id) {
        return CommonResult.success(roleService.findRoleById(id));
    }

//    @GetMapping(path = "/uc/role/findRolesByParentId/{parentId}")
//    @ApiOperation(value = "根据角色父id查询所有角色")
//    public CommonResult<List<TbRolePO>> findRolesByParentId(@ApiParam(name = "parentId", value = "父id", required = true) @PathVariable Integer parentId) {
//        return CommonResult.success(roleService.findRolesByParentId(parentId));
//    }

//    @GetMapping(path = "/uc/role/findRoleNamesByParentId/{parentId}")
//    @ApiOperation(value = "根据角色父id查询所有角色名")
//    public CommonResult<List<String>> findRoleNamesByParentId(@ApiParam(name = "parentId", value = "父id", required = true) @PathVariable Integer parentId) {
//        return CommonResult.success(roleService.findRoleNamesByParentId(parentId));
//    }

//    @PostMapping(path = "/uc/role/findRolesByFactor")
//    @ApiOperation(value = "根据条件查询角色")
//    public CommonResult<List<TbRolePO>> findRolesByFactor(@ApiParam(name = "searchFactorVO", value = "筛选信息", required = true) @RequestBody SearchFactorVO searchFactorVO) {
//        return CommonResult.success(roleService.findRolesByFactor(searchFactorVO));
//    }

    /**
     * 添加
     */
    @PostMapping(path = "/uc/role/addRole")
    @ApiOperation(value = "添加角色")
    public CommonResult<String> addRole(@ApiParam(name = "tbRolePO", value = "角色对象", required = true)
                                        @RequestBody TbRolePO tbRolePO,
                                        @ApiParam(name = "permissionIds", value = "权限id列表", required = true)
                                        @RequestBody List<Integer> permissionIds) {

        System.out.println("*****tbRolePO:" + tbRolePO);
        roleService.addRole(tbRolePO, permissionIds);
        return CommonResult.success("角色添加成功");
    }

    @Resource
    private ExpiredTokenService expiredTokenService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 修改(添加有该角色的用户到过期列表)
     */
    @PostMapping(path = "/uc/role/updateRole")
    @ApiOperation(value = "更新角色")
    public CommonResult<String> updateRole(@ApiParam(name = "tbRolePO", value = "角色对象", required = true)
                                           @RequestBody TbRolePO tbRolePO,
                                           @ApiParam(name = "permissionIds", value = "权限id列表", required = true)
                                           @RequestBody List<Integer> permissionIds) {
        roleService.updateRole(tbRolePO, permissionIds);

        // 异步添加到过期列表
        new Thread(new FutureTask<>(() -> {
            List<Integer> userIds = expiredTokenService.getUserIdsByRoles(Collections.singletonList(tbRolePO.getId()));
            userIds.forEach((administratorId) -> {
                rabbitTemplate.convertAndSend("ExpiredTokenExchange", "expiredToken.addExpiredToken", Arrays.asList(administratorId, System.currentTimeMillis() / 1000));
            });
            return 0;
        })).start();

        return CommonResult.success("角色修改成功");
    }

    /**
     * 删除(添加有该角色的用户到过期列表)
     */
    @DeleteMapping(path = "/uc/role/deleteRole/{roleId}")
    @ApiOperation(value = "删除角色")
    public CommonResult<String> deleteRole(@ApiParam(name = "roleId", value = "角色id", required = true) @PathVariable Integer roleId) {
        roleService.deleteRole(roleId);

        // 异步添加到过期列表
        new Thread(new FutureTask<>(() -> {
            List<Integer> userIds = expiredTokenService.getUserIdsByRoles(Collections.singletonList(roleId));
            userIds.forEach((administratorId) -> {
                rabbitTemplate.convertAndSend("ExpiredTokenExchange", "expiredToken.addExpiredToken", Arrays.asList(administratorId, System.currentTimeMillis() / 1000));
            });
            return 0;
        })).start();

        return CommonResult.success("角色删除成功");
    }

}
