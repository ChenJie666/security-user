package oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import oauth2.entities.po.TbRolePermissionPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 14:18
 */
@Component
public interface RolePermissionMapper extends BaseMapper<TbRolePermissionPO> {

    @Select(" select rp.id as bind_id,p.id as permission_id,p.name as permission_name" +
            " from tb_role_permission rp" +
            " left join tb_permission p" +
            " on rp.permission_id = p.id" +
            " where rp.role_id = #{roleId}")
    List<Map<String, Object>> getPermissions(@Param("roleId") Integer roleId);

}
