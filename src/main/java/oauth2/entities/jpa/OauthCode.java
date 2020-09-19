package oauth2.entities.jpa;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/23 15:03
 */
@Entity
public class OauthCode {

    @Id
    private String code;
    @Column(nullable = false,columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;
    @Column(columnDefinition = "blob")
    private byte[] authentication;

}
