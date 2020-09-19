import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import sun.misc.BASE64Decoder;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class RSATest {

    @Test
    public void test() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {

        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3XOqdP2uE1CoQ4qPUnkoWOtVtN/zP3uMCz8U05qRJN541MknOspeJqVGiS/QTvuTkW9GGJDxhOOJO9v83VzuH27cIevZXnN4UmOxGTyaHbP8fEEnn6HITT9Uy8sfo89DMZdWqToUIe9B7bDnZ+9ZJeLE7z5EuyffBLW0vroa79ANCOrrFyDTrYVajSVL64ZtpV5VU0s52EZHOXgm/QNeFqrm3iO3CVJef+whWaUAO0FHV9sUoNXE96LF+LUHohkCajxeRtiDgu5lJDsPj6E7ws/nf7DpIQ5BqaxP3BSGw2AHpkgFWC/gv6w5gv1jnrMGS11+97hBeqy1z5ae0DZthwIDAQAB-----END PUBLIC KEY-----";

        //加密生成的jwd令牌
        String jwsString = "eyJhbGciOiJSUzI1NiJ9.eyJ1c2VySWQiOjEwMDAsImlhdCI6MTYwMDUwMDA1MSwiZXhwIjoxNjAzMDkyMDUxfQ.GqkK9sigw5c9-lajTIbf1NlBZsVK7bsaOw-k1Um0uBVBF0-ACS8KCgzyFpB9ArzPxnUJJ7jNgOuZqy46msdi7ogABNUpcXg8G74xGE3QVF-7f9RrRUjUBZqjHItZbntQN5MhdXPzL9I98m3EFmTro-NPoihXxlPGCSNHKXObdS_v4sufcN132a9PIrjofS0_dmUYu_jnm1F-MusN0L9LlxgGuu262e3YdpyZF2regdEnt66IOVyKtt1kNHnNEDyyp9gtk6QF7cb7Er4anGiG1CIaJpIk6Iq7JfvotXUd0wjbT8gA0Gu_toRuVKQp9gTwuGlmmlT2dTAop9dBmbPjFQ";


        byte[] keyBytes;

        keyBytes = (new BASE64Decoder()).decodeBuffer(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey rsaPublicKey = keyFactory.generatePublic(keySpec);

        Jwts.parserBuilder().setSigningKey(rsaPublicKey).build().parseClaimsJws(jwsString);

    }

}