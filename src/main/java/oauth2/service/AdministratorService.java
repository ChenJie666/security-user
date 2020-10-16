package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.po.TbRolePO;
import oauth2.entities.po.TbUserPO;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 15:00
 */
public interface AdministratorService extends IService<TbUserPO> {

    /**
     * 查询用户信息
     * @return
     */
    Set<Integer> findBranchUserIdsByUserId(Integer userId);

    /**
     * 查询所有用户名
     * @return
     */
//    List<String> findAllUserNames();

    /**
     * 根据用户名查询用户
     * @return
     */
//    TbUserPO findUserByName(String username);

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
//    List<TbUserPO> findUsersByFactor(SearchFactorVO searchFactorVO);

    /**
     * 添加管理员
     * @param tbAdministratorPO
     */
    void addAdministrator(TbUserPO tbAdministratorPO);

    /**
     * 更新管理员
     * @param tbAdministratorPO
     */
    void updateAdministrator(TbUserPO tbAdministratorPO);

    void enableAdministrator(Integer userId);

    void changeParentId(Integer userId,Integer parentId);

    /**
     * 删除管理员
     * @param administratorId
     */
    void deleteAdministrator(Integer administratorId);

}
