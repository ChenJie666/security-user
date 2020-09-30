package oauth2.feign;

import io.swagger.annotations.Api;
import oauth2.common.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/30 10:14
 */
@RestController
@Api(value = "需要拦截的token")
public class ExpiredTokenController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private RedisKeyConfig redisKeyConfig;

    @GetMapping(path = "/feign/user/getExpiredToken")
    public Map<Object,Object> getExpiredToken(){
//        redisTemplate.opsForHash().putIfAbsent(redisKeyConfig.getExpiredTokenKey(),"admin",1601455413);
//        Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKeyConfig.getExpiredTokenKey());
//        System.out.println("feign调用成功" + entries);
        return redisTemplate.opsForHash().entries(redisKeyConfig.getExpiredTokenKey());
    }

}
