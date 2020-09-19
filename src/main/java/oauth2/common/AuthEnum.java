package oauth2.common;

import org.assertj.core.util.Arrays;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/19 16:08
 */
public enum AuthEnum {

    HIFUN(1, "Mars-Token", "火粉放置token的请求头"),

    SCOPE(2,Arrays.array(""),"客户端访问资源权限"),

    AUD(3,Arrays.array("res1"),"火粉用户拥有的权限"),

    AUTHORITIES(4,Arrays.array("hifun"),"火粉用户拥有的权限");

    private Integer code;
    private Object codeText;
    private String description;

    AuthEnum(Integer code, Object codeText, String description) {
        this.code = code;
        this.codeText = codeText;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public Object getCodeText() {
        return codeText;
    }

    public String getDescription() {
        return description;
    }


}
