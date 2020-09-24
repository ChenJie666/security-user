package oauth2.controller;

import oauth2.entities.*;
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
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 查询
     */
    @GetMapping(path = "/role/findAllRoles/{pageCurrent}/{pageSize}")
    public CommonResult<ObjListPO<TbRolePO>> findAllRoles(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize) {
        return CommonResult.success(roleService.findAllRoles(pageCurrent,pageSize));
    }

    @GetMapping(path = "/role/findAllRoleNames")
    public CommonResult<List<String>> findAllRoleNames() {
        return CommonResult.success(roleService.findAllRoleNames());
    }

    @GetMapping(path = "/role/findRoleByName/{rolename}")
    public CommonResult<TbRolePO> findRoleByName(@PathVariable String rolename) {
        return CommonResult.success(roleService.findRoleByName(rolename));
    }

    @GetMapping(path = "/role/findRoleById/{id}")
    public CommonResult<TbRolePO> findRoleById(@PathVariable Integer id) {
        return CommonResult.success(roleService.findRoleById(id));
    }

    @GetMapping(path = "/role/findRolesByParentId/{parentId}")
    public CommonResult<List<TbRolePO>> findRolesByParentId(@PathVariable Integer parentId){
        return CommonResult.success(roleService.findRolesByParentId(parentId));
    }

    @GetMapping(path = "/role/findRoleNamesByParentId/{parentId}")
    public CommonResult<List<String>> findRoleNamesByParentId(@PathVariable Integer parentId){
        return CommonResult.success(roleService.findRoleNamesByParentId(parentId));
    }

    @PostMapping(path = "/role/findRolesByFactor")
    public CommonResult<List<TbRolePO>> findRolesByFactor(@RequestBody SearchFactorVO searchFactorVO){
        return CommonResult.success(roleService.findRolesByFactor(searchFactorVO));
    }

    /**
     * 添加
     */


    /**
     * 修改
     */


    /**
     * 删除
     */

}
