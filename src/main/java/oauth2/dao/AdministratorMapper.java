package oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import oauth2.entities.po.TbUserPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 10:44
 */
@Component
public interface AdministratorMapper extends BaseMapper<TbUserPO> {

    @Select("SELECT username FROM tb_user WHERE id = #{userId}")
    String getUsernameById(@Param("userId") Integer userId);

}
