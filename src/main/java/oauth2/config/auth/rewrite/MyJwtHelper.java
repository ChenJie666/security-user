package oauth2.config.auth.rewrite;

import cn.hutool.json.JSONObject;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;

import java.util.Objects;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/19 17:03
 */
public class MyJwtHelper extends JwtHelper {

    public static Jwt decodeAndVerify(String token, SignatureVerifier verifier) {
        Jwt jwt = decode(token);
        String claims = jwt.getClaims();
        String jti = new JSONObject(claims).getStr("jti");
        if (Objects.isNull(jti)) {
            //1.如果没有jti，说明是火粉的token，用火粉的公钥验证
        }
        jwt.verifySignature(verifier);

        return jwt;
    }


}
