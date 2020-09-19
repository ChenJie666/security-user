package oauth2.controller;

import oauth2.entities.CommonResult;
import oauth2.entities.TbUserPO;
import oauth2.service.AdminService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping("/administrator/getInfo")
    public CommonResult<TbUserPO> getInfoById(){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        return CommonResult.success(adminService.getInfoById(Integer.parseInt(id)));
    }

}
