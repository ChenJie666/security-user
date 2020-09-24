package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbUserPO;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 15:00
 */
public interface AdministratorService extends IService<TbUserPO> {

    /**
     * 查询所有用户信息
     * @return
     */
    ObjListPO<TbUserPO> findAllUsers(Integer pageCurrent, Integer pageSize);

    /**
     * 查询所有用户名
     * @return
     */
    List<String> findAllUserNames();

    /**
     * 根据用户名查询用户
     * @return
     */
    TbUserPO findUserByName(String username);

    /**
     * 根据用户id查询用户
     * @return
     */
    TbUserPO findUserById(Integer id);

    /**
     * 获取筛选后的所有用户信息
     * @param searchFactorVO
     * @return
     */
    List<TbUserPO> findUsersByFactor(SearchFactorVO searchFactorVO);

}
