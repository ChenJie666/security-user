package oauth2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.entities.*;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.po.TbClientPO;
import oauth2.entities.po.TbRolePO;
import oauth2.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 16:20
 */
@RestController
@Api(value = "角色操作API",tags = {"角色信息的增删改查"})
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 查询
     */
    @GetMapping(path = "/role/findAllRoles/{pageCurrent}/{pageSize}")
    @ApiOperation(value = "查询所有的角色")
    public CommonResult<ObjListPO<TbRolePO>> findAllRoles(@ApiParam(name = "pageCurrent", value = "查询的页数",required = true)
                                                              @PathVariable Integer pageCurrent,
                                                          @ApiParam(name = "pageSize", value = "每页的记录数",required = true)
                                                              @PathVariable Integer pageSize) {
        return CommonResult.success(roleService.findAllRoles(pageCurrent,pageSize));
    }

    @GetMapping(path = "/role/findAllRoleNames")
    @ApiOperation(value = "查询所有的角色名")
    public CommonResult<List<String>> findAllRoleNames() {
        return CommonResult.success(roleService.findAllRoleNames());
    }

    @GetMapping(path = "/role/findRoleByName/{rolename}")
    @ApiOperation(value = "根据角色名查询角色")
    public CommonResult<TbRolePO> findRoleByName(@ApiParam(name = "rolename",value = "角色名",required = true) @PathVariable String rolename) {
        return CommonResult.success(roleService.findRoleByName(rolename));
    }

    @GetMapping(path = "/role/findRoleById/{id}")
    @ApiOperation(value = "根据角色id查询角色")
    public CommonResult<TbRolePO> findRoleById(@ApiParam(name = "id",value = "角色id",required = true) @PathVariable Integer id) {
        return CommonResult.success(roleService.findRoleById(id));
    }

    @GetMapping(path = "/role/findRolesByParentId/{parentId}")
    @ApiOperation(value = "根据角色父id查询所有角色")
    public CommonResult<List<TbRolePO>> findRolesByParentId(@ApiParam(name = "parentId",value = "父id",required = true) @PathVariable Integer parentId){
        return CommonResult.success(roleService.findRolesByParentId(parentId));
    }

    @GetMapping(path = "/role/findRoleNamesByParentId/{parentId}")
    @ApiOperation(value = "根据角色父id查询所有角色名")
    public CommonResult<List<String>> findRoleNamesByParentId(@ApiParam(name = "parentId",value = "父id",required = true) @PathVariable Integer parentId){
        return CommonResult.success(roleService.findRoleNamesByParentId(parentId));
    }

    @PostMapping(path = "/role/findRolesByFactor")
    @ApiOperation(value = "根据条件查询角色")
    public CommonResult<List<TbRolePO>> findRolesByFactor(@ApiParam(name = "searchFactorVO",value = "筛选信息",required = true) @RequestBody SearchFactorVO searchFactorVO){
        return CommonResult.success(roleService.findRolesByFactor(searchFactorVO));
    }

    /**
     * 添加
     */
    @PostMapping(path = "/role/addRole")
    @ApiOperation(value = "添加角色")
    public CommonResult<String> addRole(@ApiParam(name= "tbRolePO",value = "角色对象",required = true) @RequestBody TbRolePO tbRolePO) {
        System.out.println("*****tbRolePO:" + tbRolePO);
        roleService.addRole(tbRolePO);
        return CommonResult.success("角色添加成功");
    }

    /**
     * 修改
     */
    @PostMapping(path = "/role/updateRole")
    @ApiOperation(value = "更新角色")
    public CommonResult<String> updateRole(@ApiParam(name = "tbRolePO", value = "角色对象", required = true) @RequestBody TbRolePO tbRolePO) {
        System.out.println("*****tbRolePO:" + tbRolePO);
        roleService.updateRole(tbRolePO);
        return CommonResult.success("角色修改成功");
    }

    /**
     * 删除
     */
    @DeleteMapping(path = "/role/deleteRole/{roleId}")
    @ApiOperation(value = "删除角色")
    public CommonResult<String> deleteRole(@ApiParam(name = "roleId",value = "角色id",required = true) @PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
        return CommonResult.success("角色删除成功");
    }

}
