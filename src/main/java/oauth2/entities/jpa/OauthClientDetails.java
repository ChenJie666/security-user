package oauth2.entities.jpa;

import javax.persistence.*;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/23 15:02
 */
@Entity
public class OauthClientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String clientId;
    private String resourceIds;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedirectUri;
    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    @Column(length = 4096)
    private String additionalInformation;
    private String autoapprove;

}
