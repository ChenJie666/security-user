package oauth2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.entities.CommonResult;
import oauth2.service.UserRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    /**
     * 用户绑定角色增加
     */
    @PostMapping(path = "/uc/binding/addRoles/{administratorId}")
    @ApiOperation(value = "用户绑定角色增加")
    public CommonResult<String> addRoles(@ApiParam(name = "administratorId", value = "管理员id", required = true)
                                         @PathVariable Integer administratorId,
                                         @ApiParam(name = "roleIds", value = "角色id")
                                         @RequestBody List<Integer> roleIds) {
        userRoleService.addRoles(administratorId, roleIds);

        return CommonResult.success("用户绑定角色成功");
    }

    /**
     * 用户绑定角色删除
     */
    @DeleteMapping(path = "/uc/binding/deleteRoles")
    @ApiOperation(value = "用户绑定角色删除")
    public CommonResult<String> deleteRoles(@ApiParam(name = "bindIds", value = "用户角色中间表的id列表", required = true)
                                            @RequestBody List<Integer> bindIds) {
        userRoleService.deleteRoles(bindIds);

        return CommonResult.success("用户绑定角色删除成功");
    }

}
