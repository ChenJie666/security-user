package oauth2.config.auth;

import oauth2.config.auth.rewrite.MyJwtAccessTokenConverter;
import oauth2.feign.UAAClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/21 13:32
 */
@Configuration
public class TokenConfig {

//    private static final String SIGNING_KEY = "uaa123";

    @Resource
    private UAAClient uaaClient;

    /**
     * 将Jwt作为令牌
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 配置Jwt令牌（秘钥）
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        JwtAccessTokenConverter converter = new MyJwtAccessTokenConverter();
//        converter.setSigningKey(SIGNING_KEY);

        String publicKey = uaaClient.publicKey();
        System.out.println("publicKey: " + publicKey);
        converter.setVerifierKey(publicKey);
        converter.setVerifier(new RsaVerifier(publicKey));

        return converter;
    }

}
