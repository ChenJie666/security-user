package oauth2.common;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/19 16:08
 */
public enum AuthEnum {

    HIFUN(1, "Mars-Token", "火粉放置token的请求头");

    private Integer code;
    private String codeText;
    private String description;

    AuthEnum(Integer code, String codeText, String description) {
        this.code = code;
        this.codeText = codeText;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getCodeText() {
        return codeText;
    }

    public String getDescription() {
        return description;
    }


}
