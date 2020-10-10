package oauth2.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/30 13:37
 */
@Configuration
public class RedisKeyConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 用户中心的redis的key的命名
     */
    public String getBlackListRedisKey() {
        return applicationName + ":blacklist";
    }

    /**
     * 用户中心的redis的key的命名
     */
    public String getExpiredTokenKey() {
        return applicationName + ":expiredToken";
    }

}
