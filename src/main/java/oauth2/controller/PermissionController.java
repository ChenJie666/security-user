package oauth2.controller;

import oauth2.entities.CommonResult;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbPermissionPO;
import oauth2.entities.TbUserPO;
import oauth2.service.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(path = "/permission/findAllPermissions")
    public CommonResult<List<TbPermissionPO>> findAllPermissions() {
        return CommonResult.success(permissionService.findAllPermissions());
    }

    @GetMapping(path = "/permission/findAllPermissionNames")
    public CommonResult<List<String>> findAllPermissionNames() {
        return CommonResult.success(permissionService.findAllPermissionNames());
    }

    @GetMapping(path = "/permission/findPermissionByName")
    public CommonResult<TbPermissionPO> findPermissionByName() {
        return CommonResult.success(permissionService.findPermissionByName());
    }

    @GetMapping(path = "/permission/findPermissionById")
    public CommonResult<TbPermissionPO> findPermissionById() {
        return CommonResult.success(permissionService.findPermissionById());
    }

    @GetMapping(path = "/permission/findPermissionsByFactor")
    public CommonResult<List<TbPermissionPO>> findPermissionsByFactor(SearchFactorVO searchFactorVO){
        return CommonResult.success(permissionService.findPermissionsByFactor(searchFactorVO));
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
