package oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import oauth2.entities.po.TbPermissionPO;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 14:19
 */
@Component
public interface PermissionMapper extends BaseMapper<TbPermissionPO> {
}
