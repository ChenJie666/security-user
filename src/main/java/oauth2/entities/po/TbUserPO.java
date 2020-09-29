package oauth2.entities.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/21 16:19
 */
@Data
@Accessors(chain = true)
@TableName("tb_user")
@ApiModel(value = "用户类")
public class TbUserPO {

//    /**
//     * 用户名
//     */
//    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 账户没有过期
     */
    @ApiModelProperty(value = "账户没有过期",hidden = true)
    private boolean accountNonExpired = true;
    /**
     * 账户没被锁定 （是否冻结）
     */
    @ApiModelProperty(value = "账户没被锁定",hidden = true)
    private boolean accountNonLocked = true;
    /**
     * 密码没有过期
     */
    @ApiModelProperty(value = "密码没有过期",hidden = true)
    private boolean credentialsNonExpired = true;
    /**
     * 账户是否可用（是否被删除）
     */
    @ApiModelProperty(value = "账户是否可用",hidden = true)
    private boolean enabled = true;

    /*--------------------------------------------*/

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户id",hidden = true)
    private Integer id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号",hidden = true)
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱",hidden = true)
    private String email;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间",hidden = true)
    private String created;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Date updated;

}
