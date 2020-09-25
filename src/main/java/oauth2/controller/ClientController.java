package oauth2.controller;

import oauth2.entities.*;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.po.TbClientPO;
import oauth2.service.ClientService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 16:20
 */
@RestController
public class ClientController {

    @Resource
    private ClientService clientService;

    /**
     * 查询
     */
    @GetMapping(path = "/client/findAllClients/{pageCurrent}/{pageSize}")
    public CommonResult<ObjListPO<TbClientPO>> findAllClients(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize) {
        return CommonResult.success(clientService.findAllClients(pageCurrent, pageSize));
    }

    @GetMapping(path = "/client/findAllClientNames")
    public CommonResult<List<String>> findAllClientNames() {
        return CommonResult.success(clientService.findAllClientNames());
    }

    @GetMapping(path = "/client/findClientByClientId/{clientId}")
    public CommonResult<TbClientPO> findClientByName(@PathVariable String clientId) {
        return CommonResult.success(clientService.findClientByClientId(clientId));
    }

    @GetMapping(path = "/client/findClientsByFactor")
    public CommonResult<List<TbClientPO>> findClientsByFactor(SearchFactorVO searchFactorVO) {
        return CommonResult.success(clientService.findClientsByFactor(searchFactorVO));
    }

    /**
     * 添加
     */
    @PostMapping(path = "/client/addClient")
    public CommonResult<String> addClient(@RequestBody TbClientPO tbClientPO) {
        System.out.println("*****tbClientPO:" + tbClientPO);
        clientService.addClient(tbClientPO);
        return CommonResult.success("客户端添加成功");
    }

    /**
     * 修改
     */
    @PostMapping(path = "/client/updateClient")
    public CommonResult<String> updateClient(@RequestBody TbClientPO tbClientPO) {
        System.out.println("*****tbClientPO:" + tbClientPO);
        clientService.updateClient(tbClientPO);
        return CommonResult.success("客户端修改成功");
    }

    /**
     * 删除
     */
    @DeleteMapping(path = "/client/deleteClient/{clinetId}")
    public CommonResult<String> deleteClient(@PathVariable String clientId) {
        clientService.deleteClient(clientId);
        return CommonResult.success("客户端删除成功");
    }

}
