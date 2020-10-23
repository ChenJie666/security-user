package oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import oauth2.entities.po.DepartmentPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/16 13:59
 */
@Component
public interface DepartmentMapper extends BaseMapper<DepartmentPO> {

    @Select("SELECT d.level as level,d.id as id " +
            "FROM tb_user u " +
            "LEFT JOIN department d ON u.parent_id = d.id " +
            "WHERE u.id = #{userId};")
    Map<String,Object> findLevelByUserId(@Param("userId") int userId);

}
