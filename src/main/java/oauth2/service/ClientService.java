package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.po.TbClientPO;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 15:16
 */
public interface ClientService extends IService<TbClientPO> {

    /**
     * 查询所有客户端信息
     * @return
     */
    ObjListPO<TbClientPO> findAllClients(Integer pageCurrent, Integer pageSize);

    /**
     * 查询所有客户端名
     * @return
     */
    List<String> findAllClientNames();

    /**
     * 根据客户端名查询客户端信息
     * @return
     */
    TbClientPO findClientByClientId(String clientId);

    /**
     * 获取筛选后的所有客户端信息
     * @param searchFactorVO
     * @return
     */
    List<TbClientPO> findClientsByFactor(SearchFactorVO searchFactorVO);

    /**
     * 添加客户端
     * @param tbClientPO
     * @return
     */
    void addClient(TbClientPO tbClientPO);

    /**
     * 更改客户端
     * @param tbClientPO
     * @return
     */
    void updateClient(TbClientPO tbClientPO);

    /**
     * 删除客户端
     * @param clientId
     * @return
     */
    void deleteClient(String clientId);

}
