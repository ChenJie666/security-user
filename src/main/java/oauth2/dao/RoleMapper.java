package oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import oauth2.entities.po.TbRolePO;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 13:56
 */
@Component
public interface RoleMapper extends BaseMapper<TbRolePO> {
}
