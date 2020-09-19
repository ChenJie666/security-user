package oauth2.config.auth.rewrite;

import cn.hutool.json.JSONObject;
import io.jsonwebtoken.*;
import oauth2.common.AuthEnum;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.security.jwt.*;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;

import java.nio.CharBuffer;
import java.security.PublicKey;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.security.jwt.codec.Codecs.*;
import static org.springframework.security.jwt.codec.Codecs.utf8Decode;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/19 17:03
 */
public class MyJwtHelper {
    static byte[] PERIOD = utf8Encode(".");

    public static Jwt decode(String token) {
        int firstPeriod = token.indexOf('.');
        int lastPeriod = token.lastIndexOf('.');

        if (firstPeriod <= 0 || lastPeriod <= firstPeriod) {
            throw new IllegalArgumentException("JWT must have 3 tokens");
        }
        CharBuffer buffer = CharBuffer.wrap(token, 0, firstPeriod);
        // TODO: Use a Reader which supports CharBuffer
        JwtHeader header = JwtHeaderHelper.create(buffer.toString());

        buffer.limit(lastPeriod).position(firstPeriod + 1);
        byte[] claims = b64UrlDecode(buffer);

        boolean emptyCrypto = lastPeriod == token.length() - 1;

        byte[] crypto;

        if (emptyCrypto) {
            if (!"none".equals(header.parameters.alg)) {
                throw new IllegalArgumentException(
                        "Signed or encrypted token must have non-empty crypto segment");
            }
            crypto = new byte[0];
        }
        else {
            buffer.limit(token.length()).position(lastPeriod + 1);
            crypto = b64UrlDecode(buffer);
        }
        return new JwtImpl(header, claims, crypto);
    }

    public static Jwt decodeAndVerify(String token, SignatureVerifier verifier, PublicKey publicKey) {
        System.out.println("*****进入自定义的token认证方法");
        Jwt jwt = decode(token);
        String claims = jwt.getClaims();
        JSONObject jsonObject = new JSONObject(claims);
        String jti = jsonObject.getStr("jti");
        if (Objects.isNull(jti)) {
            //1.如果没有jti，说明是火粉的token，用火粉的公钥验证
            try {
                Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
                String userId = jsonObject.getStr("userId");
                jsonObject.remove("userId");
                jsonObject.putOpt("user_name", userId);
                jsonObject.putOpt("aud", AuthEnum.AUD.getCodeText());
                jsonObject.putOpt("scope", AuthEnum.SCOPE.getCodeText());
                jsonObject.putOpt("authorities", AuthEnum.AUTHORITIES.getCodeText());

                if(jwt instanceof JwtImpl) {
                    JwtImpl jwtImpl = (JwtImpl) jwt;
                    JwtHeader header = jwtImpl.header();
                    byte[] crypto = jwtImpl.getCrypto();
                    claims = jsonObject.toString();

                    jwt = new JwtImpl(header, claims, crypto);
                }
            } catch (JwtException e) {
                throw new InvalidSignatureException("RSA Signature did not match content");
            }
        } else {
            //2.否则就是oauth2的标准token
            jwt.verifySignature(verifier);
        }

        return jwt;
    }

}

/**
 * Helper object for JwtHeader.
 *
 * Handles the JSON parsing and serialization.
 */
class JwtHeaderHelper {

    static JwtHeader create(String header) {
        byte[] bytes = b64UrlDecode(header);
        return new JwtHeader(bytes, parseParams(bytes));
    }

    static HeaderParameters parseParams(byte[] header) {
        Map<String, String> map = parseMap(utf8Decode(header));
        return new HeaderParameters(map);
    }

    private static Map<String, String> parseMap(String json) {
        if (json != null) {
            json = json.trim();
            if (json.startsWith("{")) {
                return parseMapInternal(json);
            }
            else if (json.equals("")) {
                return new LinkedHashMap<String, String>();
            }
        }
        throw new IllegalArgumentException("Invalid JSON (null)");
    }

    private static Map<String, String> parseMapInternal(String json) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        json = trimLeadingCharacter(trimTrailingCharacter(json, '}'), '{');
        for (String pair : json.split(",")) {
            String[] values = pair.split(":");
            String key = strip(values[0], '"');
            String value = null;
            if (values.length > 0) {
                value = strip(values[1], '"');
            }
            if (map.containsKey(key)) {
                throw new IllegalArgumentException("Duplicate '" + key + "' field");
            }
            map.put(key, value);
        }
        return map;
    }

    private static String strip(String string, char c) {
        return trimLeadingCharacter(trimTrailingCharacter(string.trim(), c), c);
    }

    private static String trimTrailingCharacter(String string, char c) {
        if (string.length() >= 0 && string.charAt(string.length() - 1) == c) {
            return string.substring(0, string.length() - 1);
        }
        return string;
    }

    private static String trimLeadingCharacter(String string, char c) {
        if (string.length() >= 0 && string.charAt(0) == c) {
            return string.substring(1);
        }
        return string;
    }

    private static byte[] serializeParams(HeaderParameters params) {
        StringBuilder builder = new StringBuilder("{");

        appendField(builder, "alg", params.alg);
        if (params.typ != null) {
            appendField(builder, "typ", params.typ);
        }
        for (Map.Entry<String, String> entry : params.map.entrySet()) {
            appendField(builder, entry.getKey(), entry.getValue());
        }
        builder.append("}");
        return utf8Encode(builder.toString());

    }

    private static void appendField(StringBuilder builder, String name, String value) {
        if (builder.length() > 1) {
            builder.append(",");
        }
        builder.append("\"").append(name).append("\":\"").append(value).append("\"");
    }
}


/**
 * Header part of JWT
 */
class JwtHeader implements BinaryFormat {
    private final byte[] bytes;

    final HeaderParameters parameters;

    /**
     * @param bytes      the decoded header
     * @param parameters the parameter values contained in the header
     */
    JwtHeader(byte[] bytes, HeaderParameters parameters) {
        this.bytes = bytes;
        this.parameters = parameters;
    }

    @Override
    public byte[] bytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return utf8Decode(bytes);
    }
}

class HeaderParameters {
    final String alg;

    final Map<String, String> map;

    final String typ = "JWT";

    HeaderParameters(String alg) {
        this(new LinkedHashMap<String, String>(Collections.singletonMap("alg", alg)));
    }

    HeaderParameters(Map<String, String> map) {
        String alg = map.get("alg"), typ = map.get("typ");
        if (typ != null && !"JWT".equalsIgnoreCase(typ)) {
            throw new IllegalArgumentException("typ is not \"JWT\"");
        }
        map.remove("alg");
        map.remove("typ");
        this.map = map;
        if (alg == null) {
            throw new IllegalArgumentException("alg is required");
        }
        this.alg = alg;
    }

}

class JwtImpl implements Jwt {
    final JwtHeader header;

    private final byte[] content;

    private final byte[] crypto;

    private String claims;

    /**
     * @param header  the header, containing the JWS/JWE algorithm information.
     * @param content the base64-decoded "claims" segment (may be encrypted, depending on
     *                header information).
     * @param crypto  the base64-decoded "crypto" segment.
     */
    JwtImpl(JwtHeader header, byte[] content, byte[] crypto) {
        this.header = header;
        this.content = content;
        this.crypto = crypto;
        claims = utf8Decode(content);
    }

    JwtImpl(JwtHeader header, String claims, byte[] crypto) {
        this.header = header;
        this.crypto = crypto;
        this.claims = claims;
        content = utf8Encode(claims);
    }

    /**
     * Validates a signature contained in the 'crypto' segment.
     *
     * @param verifier the signature verifier
     */
    @Override
    public void verifySignature(SignatureVerifier verifier) {
        verifier.verify(signingInput(), crypto);
    }

    private byte[] signingInput() {
        return concat(b64UrlEncode(header.bytes()), MyJwtHelper.PERIOD,
                b64UrlEncode(content));
    }

    /**
     * Allows retrieval of the full token.
     *
     * @return the encoded header, claims and crypto segments concatenated with "."
     * characters
     */
    @Override
    public byte[] bytes() {
        return concat(b64UrlEncode(header.bytes()), MyJwtHelper.PERIOD,
                b64UrlEncode(content), MyJwtHelper.PERIOD, b64UrlEncode(crypto));
    }

    @Override
    public String getClaims() {
        return utf8Decode(content);
    }

    @Override
    public String getEncoded() {
        return utf8Decode(bytes());
    }

    public JwtHeader header() {
        return this.header;
    }

    public byte[] getCrypto() {
        return this.crypto;
    }

    @Override
    public String toString() {
        return header + " " + claims + " [" + crypto.length + " crypto bytes]";
    }
}