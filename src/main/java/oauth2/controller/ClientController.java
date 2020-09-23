package oauth2.controller;

import oauth2.entities.CommonResult;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbClientPO;
import oauth2.entities.TbUserPO;
import oauth2.service.ClientService;
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
public class ClientController {

    @Resource
    private ClientService clientService;

    /**
     * 查询
     */
    @GetMapping(path = "/client/findAllClients")
    public CommonResult<List<TbClientPO>> findAllClients() {
        return CommonResult.success(clientService.findAllClients());
    }

    @GetMapping(path = "/client/findAllClientNames")
    public CommonResult<List<String>> findAllClientNames() {
        return CommonResult.success(clientService.findAllClientNames());
    }

    @GetMapping(path = "/client/findClientByName")
    public CommonResult<TbClientPO> findClientByName() {
        return CommonResult.success(clientService.findClientByName());
    }

    @GetMapping(path = "/client/findClientsByFactor")
    public CommonResult<List<TbClientPO>> findClientsByFactor(SearchFactorVO searchFactorVO){
        return CommonResult.success(clientService.findClientsByFactor(searchFactorVO));
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
