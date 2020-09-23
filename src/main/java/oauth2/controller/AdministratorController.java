package oauth2.controller;

import oauth2.entities.*;
import oauth2.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(path = "/administrator/findAllUsers")
    public CommonResult<List<TbUserPO>> findAllUsers() {
        return CommonResult.success(adminService.findAllUsers());
    }

    @GetMapping(path = "/administrator/findAllNames")
    public CommonResult<List<String>> findAllNames() {
        return CommonResult.success(adminService.findAllUserNames());
    }

    @GetMapping(path = "/administrator/findUserByName")
    public CommonResult<TbUserPO> findUserByName(String username) {
        return CommonResult.success(adminService.findUserByName(username));
    }

    @GetMapping(path = "/administrator/findUserById")
    public CommonResult<TbUserPO> findUserById(Integer id) {
        return CommonResult.success(adminService.findUserById(id));
    }

    @GetMapping(path = "/administrator/findUsersByFactor")
    public CommonResult<List<TbUserPO>> findUsersByFactor(SearchFactorVO searchFactorVO){
        return CommonResult.success(adminService.findUsersByFactor(searchFactorVO));
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
