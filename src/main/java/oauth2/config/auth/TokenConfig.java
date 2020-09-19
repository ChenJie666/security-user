package oauth2.config.auth;

import oauth2.config.auth.rewrite.MyJwtAccessTokenConverter;
import oauth2.feign.UAAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

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

    @Resource
    private Environment environment;

    @Bean
    public PublicKey hifunPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String hifunPublicKey = environment.getProperty("jwt.publicKey");
        System.out.println("***publicKeyStr：" + hifunPublicKey);
        byte[]         keyBytes = (new BASE64Decoder()).decodeBuffer(hifunPublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey rsaPublicKey = keyFactory.generatePublic(keySpec);
        return rsaPublicKey;
    }

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
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        MyJwtAccessTokenConverter converter = new MyJwtAccessTokenConverter();
//        converter.setSigningKey(SIGNING_KEY);

        String publicKey = uaaClient.publicKey();
        System.out.println("publicKey: " + publicKey);
        converter.setVerifierKey(publicKey);
        converter.setVerifier(new RsaVerifier(publicKey));

        return converter;
    }

}
