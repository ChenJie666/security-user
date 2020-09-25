package oauth2.controller;

import oauth2.entities.*;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.po.TbRolePO;
import oauth2.entities.po.TbUserPO;
import oauth2.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 11:28
 */
@RestController
public class AdministratorController {

    @Resource
    private AdministratorService adminService;


    /**
     * 查询
     */
    @GetMapping(path = "/administrator/findAllUsers/{pageCurrent}/{pageSize}")
    public CommonResult<ObjListPO<TbUserPO>> findAllUsers(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize) {
        return CommonResult.success(adminService.findAllUsers(pageCurrent, pageSize));
    }

    @GetMapping(path = "/administrator/findAllNames")
    public CommonResult<List<String>> findAllNames() {
        return CommonResult.success(adminService.findAllUserNames());
    }

    @GetMapping(path = "/administrator/findUserByName/{username}")
    public CommonResult<TbUserPO> findUserByName(@PathVariable String username) {
        return CommonResult.success(adminService.findUserByName(username));
    }

    @GetMapping(path = "/administrator/findUserById/{id}")
    public CommonResult<TbUserPO> findUserById(@PathVariable Integer id) {
        return CommonResult.success(adminService.findUserById(id));
    }

    @PostMapping(path = "/administrator/findUsersByFactor")
    public CommonResult<List<TbUserPO>> findUsersByFactor(@RequestBody SearchFactorVO searchFactorVO){
        return CommonResult.success(adminService.findUsersByFactor(searchFactorVO));
    }

    /**
     * 添加
     */
    @PostMapping(path = "/administrator/addAdministrator")
    public CommonResult<String> addAdministrator(@RequestBody TbUserPO tbAdministratorPO) {
        System.out.println("*****tbAdministratorPO:" + tbAdministratorPO);
        adminService.addAdministrator(tbAdministratorPO);
        return CommonResult.success("角色添加成功");
    }

    /**
     * 修改
     */
    @PostMapping(path = "/administrator/updateAdministrator")
    public CommonResult<String> updateAdministrator(@RequestBody TbUserPO tbAdministratorPO) {
        System.out.println("*****tbAdministratorPO:" + tbAdministratorPO);
        adminService.updateAdministrator(tbAdministratorPO);
        return CommonResult.success("角色修改成功");
    }

    /**
     * 删除
     */
    @DeleteMapping(path = "/administrator/deleteAdministrator/{administratorId}")
    public CommonResult<String> deleteAdministrator(@PathVariable Integer administratorId) {
        adminService.deleteAdministrator(administratorId);
        return CommonResult.success("角色删除成功");
    }


}
