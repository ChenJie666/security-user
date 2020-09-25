package oauth2.controller;

import oauth2.entities.*;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.po.TbPermissionPO;
import oauth2.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 16:20
 */
@RestController
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 查询
     */
    @GetMapping(path = "/permission/findAllPermissions/{pageCurrent}/{pageSize}")
    public CommonResult<ObjListPO<TbPermissionPO>> findAllPermissions(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize) {
        return CommonResult.success(permissionService.findAllPermissions(pageCurrent,pageSize));
    }

    @GetMapping(path = "/permission/findAllPermissionNames")
    public CommonResult<List<String>> findAllPermissionNames() {
        return CommonResult.success(permissionService.findAllPermissionNames());
    }

    @GetMapping(path = "/permission/findPermissionByName/{permissionname}")
    public CommonResult<TbPermissionPO> findPermissionByName(@PathVariable String permissionname) {
        return CommonResult.success(permissionService.findPermissionByName(permissionname));
    }

    @GetMapping(path = "/permission/findPermissionById/{id}")
    public CommonResult<TbPermissionPO> findPermissionById(@PathVariable Integer id) {
        return CommonResult.success(permissionService.findPermissionById(id));
    }

    @GetMapping(path = "/permission/findPermissionsByParentId/{parentId}")
    public CommonResult<List<TbPermissionPO>> findPermissionsByParentId(@PathVariable Integer parentId){
        return CommonResult.success(permissionService.findPermissionsByParentId(parentId));
    }

    @GetMapping(path = "/permission/findPermissionNamesByParentId/{parentId}")
    public CommonResult<List<String>> findPermissionNamesByParentId(@PathVariable Integer parentId){
        return CommonResult.success(permissionService.findPermissionNamesByParentId(parentId));
    }

    @PostMapping(path = "/permission/findPermissionsByFactor")
    public CommonResult<List<TbPermissionPO>> findPermissionsByFactor(@RequestBody SearchFactorVO searchFactorVO){
        return CommonResult.success(permissionService.findPermissionsByFactor(searchFactorVO));
    }

    /**
     * 添加
     */
    @PostMapping(path = "/permission/addPermission")
    public CommonResult<String> addPermission(@RequestBody TbPermissionPO tbPermissionPO) {
        System.out.println("*****tbPermissionPO:" + tbPermissionPO);
        permissionService.addPermission(tbPermissionPO);
        return CommonResult.success("权限添加成功");
    }

    /**
     * 修改
     */
    @PostMapping(path = "/permission/updatePermission")
    public CommonResult<String> updatePermission(@RequestBody TbPermissionPO tbPermissionPO) {
        System.out.println("*****tbPermissionPO:" + tbPermissionPO);
        permissionService.updatePermission(tbPermissionPO);
        return CommonResult.success("权限修改成功");
    }

    /**
     * 删除
     */
    @DeleteMapping(path = "/permission/deletePermission/{permissionId}")
    public CommonResult<String> deletePermission(@PathVariable Integer permissionId) {
        permissionService.deletePermission(permissionId);
        return CommonResult.success("权限删除成功");
    }

}
