package oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import oauth2.entities.po.TbUserRolePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 14:17
 */
@Component
public interface UserRoleMapper extends BaseMapper<TbUserRolePO> {

    @Select(" select ur.id as bind_id,r.id as role_id,r.name as role_name" +
            " from tb_user_role ur " +
            " left join tb_role r " +
            " on ur.role_id = r.id " +
            " where ur.user_id = #{administratorId}")
    List<Map<String, Object>> getRoles(@Param("administratorId") Integer administratorId);

}
