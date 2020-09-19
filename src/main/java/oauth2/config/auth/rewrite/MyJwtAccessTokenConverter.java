package oauth2.config.auth.rewrite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/19 16:41
 */
@Component
public class MyJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private static final Log logger = LogFactory.getLog(JwtAccessTokenConverter.class);

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    private JwtClaimsSetVerifier jwtClaimsSetVerifier = new NoOpJwtClaimsSetVerifier();

    private JsonParser objectMapper = JsonParserFactory.create();

    private String verifierKey = new RandomValueStringGenerator().generate();

    private Signer signer = new MacSigner(verifierKey);

    private String signingKey = verifierKey;

    private SignatureVerifier verifier;

    @Override
    protected Map<String, Object> decode(String token) {
        try {
            Jwt jwt = MyJwtHelper.decodeAndVerify(token, verifier);
            String claimsStr = jwt.getClaims();
            Map<String, Object> claims = objectMapper.parseMap(claimsStr);
            if (claims.containsKey(EXP) && claims.get(EXP) instanceof Integer) {
                Integer intValue = (Integer) claims.get(EXP);
                claims.put(EXP, new Long(intValue));
            }
            this.getJwtClaimsSetVerifier().verify(claims);
            return claims;
        }
        catch (Exception e) {
            throw new InvalidTokenException("Cannot convert access token to JSON", e);
        }
    }

    private boolean isPublic(String key) {
        return key.startsWith("-----BEGIN");
    }

    private class NoOpJwtClaimsSetVerifier implements JwtClaimsSetVerifier {
        @Override
        public void verify(Map<String, Object> claims) throws InvalidTokenException {
        }
    }

}
