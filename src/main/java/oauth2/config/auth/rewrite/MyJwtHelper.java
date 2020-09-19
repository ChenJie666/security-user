package oauth2.config.auth.rewrite;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/19 17:03
 */
public class MyJwtHelper extends JwtHelper {

    public static Jwt decodeAndVerify(String token, SignatureVerifier verifier) {
        Jwt jwt = decode(token);
        jwt.verifySignature(verifier);

        return jwt;
    }

}
