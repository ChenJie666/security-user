package oauth2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import oauth2.entities.*;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.po.TbClientPO;
import oauth2.service.ClientService;
import oauth2.utils.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 16:20
 */
@RestController
@Api(value = "客户端操作API", tags = {"客户端信息"})
public class ClientController {

    @Resource
    private ClientService clientService;

    /**
     * 查询
     */
    @GetMapping(path = "/uc/client/findAllClients")
    @ApiOperation(value = "查询所有的客户端")
    public CommonResult<ObjListPO<TbClientPO>> findAllClients(@ApiParam(name = "page", value = "查询的页数", required = true)
                                                              @RequestParam Integer page,
                                                              @ApiParam(name = "pageSize", value = "每页的记录数", required = true)
                                                              @RequestParam Integer pageSize) {
        return CommonResult.success(clientService.findAllClients(page, pageSize));
    }

    @GetMapping(path = "/uc/client/findAllClientNames")
    @ApiOperation(value = "查询所有的客户端名")
    public CommonResult<List<String>> findAllClientNames() {
        return CommonResult.success(clientService.findAllClientNames());
    }

    @GetMapping(path = "/uc/client/findClientByClientId/{clientId}")
    @ApiOperation(value = "根据客户端名查询客户端")
    public CommonResult<TbClientPO> findClientByName(@ApiParam(name = "clientId",value = "客户端名",required = true) @PathVariable String clientId) {
        return CommonResult.success(clientService.findClientByClientId(clientId));
    }

    @GetMapping(path = "/uc/client/findClientsByFactor")
    @ApiOperation(value = "根据条件查询客户端")
    public CommonResult<List<TbClientPO>> findClientsByFactor(@ApiParam(name = "searchFactorVO",value = "筛选信息",required = true) SearchFactorVO searchFactorVO) {
        return CommonResult.success(clientService.findClientsByFactor(searchFactorVO));
    }

    /**
     * 添加
     */
    @PostMapping(path = "/uc/client/addClient")
    @ApiOperation(value = "添加客户端")
    public CommonResult<String> addClient(@ApiParam(name = "tbClientPO", value = "客户端对象", required = true) @RequestBody TbClientPO tbClientPO) {
        System.out.println("*****tbClientPO:" + tbClientPO);
        clientService.addClient(tbClientPO);
        return CommonResult.success("客户端添加成功");
    }

    /**
     * 修改
     */
    @PostMapping(path = "/uc/client/updateClient")
    @ApiOperation(value = "更新客户端")
    public CommonResult<String> updateClient(@ApiParam(name = "tbClientPO", value = "客户端对象", required = true) @RequestBody TbClientPO tbClientPO) {
        System.out.println("*****tbClientPO:" + tbClientPO);
        clientService.updateClient(tbClientPO);
        return CommonResult.success("客户端修改成功");
    }

    /**
     * 删除
     */
    @DeleteMapping(path = "/uc/client/deleteClient/{clinetId}")
    @ApiOperation(value = "删除客户端")
    public CommonResult<String> deleteClient(@ApiParam(name = "clientId", value = "客户端id", required = true) @PathVariable String clientId) {
        clientService.deleteClient(clientId);
        return CommonResult.success("客户端删除成功");
    }

}
