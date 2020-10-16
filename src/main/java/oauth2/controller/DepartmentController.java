package oauth2.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.entities.po.DepartmentPO;
import oauth2.service.DepartmentService;
import oauth2.utils.CommonResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/16 13:40
 */
@RestController
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    /**
     * 查部门和人员
     */
    @GetMapping("/uc/department/findAllDepartments")
    @ApiOperation(value = "查询所有部门和人员")
    public CommonResult<DepartmentPO> findAllDepartment() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return CommonResult.success(departmentService.findAllDepartments(Integer.parseInt(userId)));
    }

//    @GetMapping("/uc/department/findBranchDepartmentsByUserId")
//    @ApiOperation(value = "查询该用户的所有子部门")
//    public CommonResult<DepartmentPO> findMyDepartment(){
//        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//        return CommonResult.success(departmentService.findBranchDepartmentsByUserId(Integer.parseInt(userId)));
//    }

    /**
     * 添加部门
     */
    @PostMapping("/uc/department/addDepartment")
    @ApiOperation(value = "添加部门")
    public CommonResult<String> addDepartment(@ApiParam(name = "departmentPO", value = "部门对象")
                                              @RequestBody DepartmentPO departmentPO) {
        departmentService.addDepartment(departmentPO);
        return CommonResult.success("添加部门成功");
    }

    /**
     * 修改部门
     */
    @PostMapping("/uc/department/updateDepartment")
    @ApiOperation(value = "更新部门")
    public CommonResult<String> updateDepartment(@ApiParam(name = "departmentPO", value = "部门对象")
                                                 @RequestBody DepartmentPO departmentPO) {
        departmentService.updateDepartment(departmentPO);
        return CommonResult.success("更新部门成功");
    }

    /**
     * 修改部门
     */
    @PostMapping("/uc/department/changeParentId")
    @ApiOperation(value = "更新部门层级")
    public CommonResult<String> changeParentId(@ApiParam(name = "departmentId", value = "部门id")
                                               @RequestParam Integer departmentId,
                                               @ApiParam(name = "parentId", value = "父部门id")
                                               @RequestParam Integer parentId) {
        departmentService.changeParentId(departmentId, parentId);
        return CommonResult.success("更新部门层级成功");
    }

    /**
     * 删除部门
     */
    @PostMapping("/uc/department/deleteDepartment/departmentId")
    @ApiOperation(value = "删除部门")
    public CommonResult<String> deleteDepartment(@ApiParam(name = "departmentId", value = "部门id")
                                                 @PathVariable("departmentId") Integer departmentId) {
        departmentService.deleteDepartment(departmentId);
        return CommonResult.success("删除部门成功");
    }

}
