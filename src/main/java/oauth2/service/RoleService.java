package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.po.TbRolePO;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 15:01
 */
public interface RoleService extends IService<TbRolePO> {

    /**
     * 查询所有角色信息
     * @return
     */
    ObjListPO<TbRolePO> findAllRoles(Integer pageCurrent, Integer pageSize);

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
     * 根据角色父id查询所有子角色名
     * @return
     */
    List<String> findRoleNamesByParentId(Integer parentId);

    /**
     * 获取筛选后的所有角色信息
     * @param searchFactorVO
     * @return
     */
    List<TbRolePO> findRolesByFactor(SearchFactorVO searchFactorVO);

    /**
     * 添加角色
     * @param tbRolePO
     */
    void addRole(TbRolePO tbRolePO);

    /**
     * 更新角色
     * @param tbRolePO
     */
    void updateRole(TbRolePO tbRolePO);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteRole(Integer roleId);
}
