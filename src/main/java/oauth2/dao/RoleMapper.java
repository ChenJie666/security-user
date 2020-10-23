package oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import oauth2.entities.po.TbRolePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 13:56
 */
@Component
public interface RoleMapper extends BaseMapper<TbRolePO> {

    @Select("<script>" +
            "SELECT r.* " +
            "FROM tb_user u " +
            "RIGHT JOIN tb_role r ON u.id = r.parent_id " +
            "WHERE u.id IN " +
            "<foreach collection='userIds' item='userId' open='(' separator=',' close=')'> " +
            "#{userId} " +
            "</foreach> " +
            "GROUP BY r.id;" +
            "</script>")
    List<TbRolePO> findMyRoles(@Param("userIds") Set<Integer> userIds);

    @Select("SELECT r.* " +
            "FROM tb_user u " +
            "LEFT JOIN tb_user_role ur ON u.id = ur.user_id " +
            "LEFT JOIN tb_role r ON ur.role_id = r.id " +
            "WHERE u.id = #{userId};")
    List<TbRolePO> findRolesByUserId(@Param("userId") Integer userId);

}
