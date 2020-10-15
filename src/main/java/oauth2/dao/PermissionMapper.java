package oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import oauth2.entities.po.TbPermissionPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 14:19
 */
@Component
public interface PermissionMapper extends BaseMapper<TbPermissionPO> {

    @Select("select p.* " +
            "from tb_user u " +
            "left join tb_user_role ur on u.id = ur.user_id " +
            "left join tb_role_permission rp on ur.role_id = rp.role_id " +
            "left join tb_permission p on p.id = rp.permission_id " +
            "where u.id = #{userId};")
    List<TbPermissionPO> getPermissions(@Param("userId") Integer userId);

}
