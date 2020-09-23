package oauth2.service;

import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbRolePO;
import oauth2.entities.TbUserPO;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 15:01
 */
public interface RoleService {

    /**
     * 查询所有角色信息
     * @return
     */
    List<TbRolePO> findAllRoles();

    /**
     * 查询所有角色名
     * @return
     */
    List<String> findAllRoleNames();

    /**
     * 根据角色名查询角色信息
     * @return
     */
    TbRolePO findRoleByName(String rolename);

    /**
     * 根据角色id查询角色信息
     * @return
     */
    TbRolePO findRoleById(Integer id);

    /**
     * 根据角色父id查询所有子角色信息
     * @return
     */
    List<TbRolePO> findRolesByParentId(Integer parentId);

    /**
     * 获取筛选后的所有角色信息
     * @param searchFactorVO
     * @return
     */
    List<TbRolePO> findRolesByFactor(SearchFactorVO searchFactorVO);
}
