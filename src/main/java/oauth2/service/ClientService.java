package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbClientPO;
import oauth2.entities.TbUserPO;

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
    TbClientPO findClientByName(String clientId);

    /**
     * 获取筛选后的所有客户端信息
     * @param searchFactorVO
     * @return
     */
    List<TbClientPO> findClientsByFactor(SearchFactorVO searchFactorVO);
}
