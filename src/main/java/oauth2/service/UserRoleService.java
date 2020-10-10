package oauth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import oauth2.entities.po.TbUserRolePO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 14:16
 */
public interface UserRoleService extends IService<TbUserRolePO> {

    List<Map<String, Object>> getRoles(Integer administratorId);

    void addRoles(Integer administratorId, List<Integer> roleIds);

    void deleteRoles(List<Integer> bindIds);

}
