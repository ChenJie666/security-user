package oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import oauth2.entities.TbClientPO;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 14:33
 */
@Component
public interface ClientMapper extends BaseMapper<TbClientPO> {
}
