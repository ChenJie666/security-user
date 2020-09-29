package oauth2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.entities.*;
import oauth2.entities.jpa.TbUser;
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
@Api(value = "管理员操作API", tags = {"管理员信息的增删改查"})
public class AdministratorController {

    @Resource
    private AdministratorService adminService;


    /**
     * 查询
     */
    @GetMapping(path = "/uc/administrator/findAllUsers/{pageCurrent}/{pageSize}")
    @ApiOperation(value = "查询所有的管理员")
    public CommonResult<ObjListPO<TbUserPO>> findAllUsers(@ApiParam(name = "pageCurrent", value = "查询的页数",required = true)
                                                          @PathVariable Integer pageCurrent,
                                                          @ApiParam(name = "pageSize", value = "每页的记录数",required = true)
                                                          @PathVariable Integer pageSize) {
        return CommonResult.success(adminService.findAllUsers(pageCurrent, pageSize));
    }

    @GetMapping(path = "/uc/administrator/findAllNames")
    @ApiOperation(value = "查询所有的管理员名")
    public CommonResult<List<String>> findAllNames() {
        return CommonResult.success(adminService.findAllUserNames());
    }

    @GetMapping(path = "/uc/administrator/findUserByName/{username}")
    @ApiOperation(value = "根据管理员名查询管理员")
    public CommonResult<TbUserPO> findUserByName(@ApiParam(name = "username",value = "用户名",required = true) @PathVariable String username) {
        return CommonResult.success(adminService.findUserByName(username));
    }

    @GetMapping(path = "/uc/administrator/findUserById/{id}")
    @ApiOperation(value = "根据管理员id查询管理员")
    public CommonResult<TbUserPO> findUserById(@ApiParam(name = "id",value = "管理员id",required = true) @PathVariable Integer id) {
        return CommonResult.success(adminService.findUserById(id));
    }

    @PostMapping(path = "/uc/administrator/findUsersByFactor")
    @ApiOperation(value = "根据条件查询管理员")
    public CommonResult<List<TbUserPO>> findUsersByFactor(@ApiParam(name = "searchFactorVO",value = "筛选信息",required = true) @RequestBody SearchFactorVO searchFactorVO) {
        return CommonResult.success(adminService.findUsersByFactor(searchFactorVO));
    }

    /**
     * 添加
     */
    @PostMapping(path = "/uc/administrator/addAdministrator")
    @ApiOperation(value = "添加管理员")
    public CommonResult<String> addAdministrator(@ApiParam(name= "tbAdministratorPO",value = "管理员对象",required = true) @RequestBody TbUserPO tbAdministratorPO) {
        System.out.println("*****tbAdministratorPO:" + tbAdministratorPO);
        adminService.addAdministrator(tbAdministratorPO);
        return CommonResult.success("管理员添加成功");
    }

    /**
     * 修改
     */
    @PostMapping(path = "/uc/administrator/updateAdministrator")
    @ApiOperation(value = "更新管理员")
    public CommonResult<String> updateAdministrator(@ApiParam(name = "tbAdministratorPO",value = "管理员对象",required = true) @RequestBody TbUserPO tbAdministratorPO) {
        System.out.println("*****tbAdministratorPO:" + tbAdministratorPO);
        adminService.updateAdministrator(tbAdministratorPO);
        return CommonResult.success("管理员修改成功");
    }

    /**
     * 删除
     */
    @DeleteMapping(path = "/uc/administrator/deleteAdministrator/{administratorId}")
    @ApiOperation(value = "删除管理员")
    public CommonResult<String> deleteAdministrator(@ApiParam(name = "administratorId",value = "管理员id",required = true) @PathVariable Integer administratorId) {
        adminService.deleteAdministrator(administratorId);
        return CommonResult.success("管理员删除成功");
    }


}
