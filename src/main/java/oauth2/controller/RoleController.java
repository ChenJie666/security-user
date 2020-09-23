package oauth2.controller;

import oauth2.entities.CommonResult;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbRolePO;
import oauth2.entities.TbUserPO;
import oauth2.service.RoleService;
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
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 查询
     */
    @GetMapping(path = "/role/findAllRoles")
    public CommonResult<List<TbRolePO>> findAllRoles() {
        return CommonResult.success(roleService.findAllRoles());
    }

    @GetMapping(path = "/role/findAllRoleNames")
    public CommonResult<List<String>> findAllRoleNames() {
        return CommonResult.success(roleService.findAllRoleNames());
    }

    @GetMapping(path = "/role/findRoleByName")
    public CommonResult<TbRolePO> findRoleByName(String rolename) {
        return CommonResult.success(roleService.findRoleByName(rolename));
    }

    @GetMapping(path = "/role/findRoleById")
    public CommonResult<TbRolePO> findRoleById(Integer id) {
        return CommonResult.success(roleService.findRoleById(id));
    }

    @GetMapping(path = "/role/findRolesByParentId")
    public CommonResult<List<TbRolePO>> findRolesByParentId(Integer parentId){
        return CommonResult.success(roleService.findRolesByParentId(parentId));
    }

    @GetMapping(path = "/role/findRolesByFactor")
    public CommonResult<List<TbRolePO>> findRolesByFactor(SearchFactorVO searchFactorVO){
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
