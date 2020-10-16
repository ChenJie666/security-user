package oauth2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.entities.*;
import oauth2.entities.po.TbUserPO;
import oauth2.service.*;
import oauth2.utils.CommonResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 11:28
 */
@RestController
@Api(value = "管理员操作API", tags = {"管理员信息"})
public class AdministratorController {

    @Resource
    private AdministratorService adminService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 查询
     */
//    @GetMapping(path = "/uc/administrator/findMyUsers")
//    @ApiOperation(value = "查询该用户和其子用户")
//    public CommonResult<TbUserPO> findMyUsers() {
//        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        return CommonResult.success(adminService.findMyUsers(Integer.parseInt(userId)));
//    }

//    @GetMapping(path = "/uc/administrator/findAllNames")
//    @ApiOperation(value = "查询所有的管理员名")
//    public CommonResult<List<String>> findAllNames() {
//        return CommonResult.success(adminService.findAllUserNames());
//    }

//    @GetMapping(path = "/uc/administrator/findUserByName/{username}")
//    @ApiOperation(value = "根据管理员名查询管理员")
//    public CommonResult<TbUserPO> findUserByName(@ApiParam(name = "username", value = "用户名", required = true) @PathVariable String username) {
//        return CommonResult.success(adminService.findUserByName(username));
//    }
    @GetMapping(path = "/uc/administrator/findUserById/{id}")
    @ApiOperation(value = "根据管理员id查询管理员")
    public CommonResult<TbUserPO> findUserById(@ApiParam(name = "id", value = "管理员id", required = true)
                                               @PathVariable Integer id) {
        return CommonResult.success(adminService.findUserById(id));
    }

//    @PostMapping(path = "/uc/administrator/findUsersByFactor")
//    @ApiOperation(value = "根据条件查询管理员")
//    public CommonResult<List<TbUserPO>> findUsersByFactor(@ApiParam(name = "searchFactorVO", value = "筛选信息", required = true) @RequestBody SearchFactorVO searchFactorVO) {
//        return CommonResult.success(adminService.findUsersByFactor(searchFactorVO));
//    }

    /**
     * 添加
     */
    @PostMapping(path = "/uc/administrator/addAdministrator")
    @ApiOperation(value = "添加管理员")
    public CommonResult<String> addAdministrator(@ApiParam(name = "tbAdministratorPO", value = "管理员对象", required = true)
                                                 @RequestBody TbUserPO tbAdministratorPO) {
        System.out.println("*****tbAdministratorPO:" + tbAdministratorPO);
        adminService.addAdministrator(tbAdministratorPO);
        return CommonResult.success("管理员添加成功");
    }

    /**
     * 修改
     */
    @PostMapping(path = "/uc/administrator/updateAdministrator")
    @ApiOperation(value = "修改管理员信息，不能修改管理员的级属关系")
    public CommonResult<String> updateAdministrator(@ApiParam(name = "tbAdministratorPO", value = "管理员对象", required = true)
                                                    @RequestBody TbUserPO tbAdministratorPO) {
        System.out.println("*****tbAdministratorPO:" + tbAdministratorPO);
        adminService.updateAdministrator(tbAdministratorPO);
        return CommonResult.success("管理员修改成功");
    }

    @PostMapping(path = "/uc/administrator/enableAdministrator/{userId}")
    @ApiOperation(value = "更改管理员是否有效")
    public CommonResult<String> enableAdministrator(@ApiParam(name = "userId", value = "用户id", required = true)
                                                    @PathVariable Integer userId) {
        adminService.enableAdministrator(userId);
        return CommonResult.success("管理员有效性修改成功");
    }

    @PostMapping(path = "/uc/administrator/changeParentId")
    @ApiOperation(value = "修改部门的级属关系")
    public CommonResult<String> changeParentId(@ApiParam(name = "userId", value = "用户id", required = true)
                                               @RequestParam Integer userId,
                                               @ApiParam(name = "parentId", value = "部门id", required = true)
                                               @RequestParam Integer parentId) {
        adminService.changeParentId(userId, parentId);
        return CommonResult.success("管理员部门修改成功");
    }

    /**
     * 删除(添加到过期名单中)
     */
    @DeleteMapping(path = "/uc/administrator/deleteAdministrator/{administratorId}")
    @ApiOperation(value = "删除管理员")
    public CommonResult<String> deleteAdministrator(@ApiParam(name = "administratorId", value = "管理员id", required = true)
                                                    @PathVariable Integer administratorId) {
        adminService.deleteAdministrator(administratorId);

        // 将该用户添加到redis中
        rabbitTemplate.convertAndSend("ExpiredTokenExchange",
                "expiredToken.addExpiredToken",
                Arrays.asList(administratorId, System.currentTimeMillis() / 1000));

        return CommonResult.success("管理员删除成功");
    }

}
