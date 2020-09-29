package oauth2.entities.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 15:33
 */
@Data
@Accessors(chain = true)
@TableName("oauth_client_details")
@ApiModel(value = "客户端类")
public class TbClientPO {

    @TableId
    @ApiModelProperty(value = "客户端名")
    private String clientId;
    @ApiModelProperty(value = "客户端可访问的资源id")
    private String resourceIds;
    @ApiModelProperty(value = "客户端密码")
    private String clientSecret;
    @ApiModelProperty(value = "客户端权限范围(授权码模式需要对应上)")
    private String scope;
    @ApiModelProperty(value = "客户端允许的授权类型")
    private String authorizedGrantTypes;
    @ApiModelProperty(value = "客户端重定向地址")
    private String webServerRedirectUri;
    @ApiModelProperty(value = "客户端权限(暂时没用)")
    private String authorities;
    @ApiModelProperty(value = "访问令牌的有效时间")
    private Integer accessTokenValidity;
    @ApiModelProperty(value = "刷新令牌的有效时间")
    private Integer refreshTokenValidity;
    @ApiModelProperty(value = "客户端的信息")
    private String additionalInformation;
    @ApiModelProperty(value = "该客户端是否进行预授权")
    private String autoapprove;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date created;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Date updated;

}
