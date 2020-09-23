package oauth2.service;

import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbClientPO;
import oauth2.entities.TbUserPO;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 15:16
 */
public interface ClientService {

    /**
     * 查询所有客户端信息
     * @return
     */
    List<TbClientPO> findAllClients();

    /**
     * 查询所有客户端名
     * @return
     */
    List<String> findAllClientNames();

    /**
     * 根据客户端名查询客户端信息
     * @return
     */
    TbClientPO findClientByName();

    /**
     * 获取筛选后的所有客户端信息
     * @param searchFactorVO
     * @return
     */
    List<TbClientPO> findClientsByFactor(SearchFactorVO searchFactorVO);
}
