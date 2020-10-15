package oauth2.entities.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/21 16:19
 */
@Data
@Accessors(chain = true)
@TableName("tb_user")
@ApiModel(value = "用户类")
public class TbUserPO implements Serializable {
    private static final long SerialVersionUID = Long.MIN_VALUE;

//    /**
//     * 用户名
//     */
//    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    /**
     * 账户没有过期
     */
    @ApiModelProperty(value = "账户没有过期",hidden = true)
    private Boolean accountNonExpired = true;
    /**
     * 账户没被锁定 （是否冻结）
     */
    @ApiModelProperty(value = "账户没被锁定",hidden = true)
    private Boolean accountNonLocked = true;
    /**
     * 密码没有过期
     */
    @ApiModelProperty(value = "密码没有过期",hidden = true)
    private Boolean credentialsNonExpired = true;
    /**
     * 账户是否可用（是否被删除）
     */
    @ApiModelProperty(value = "账户是否可用",hidden = true)
    private Boolean enabled = true;

    /*--------------------------------------------*/

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户id",hidden = true)
    private Integer id;
    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private Integer parentId;
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

    @ApiModelProperty(value = "创建者id",hidden = true)
    private Integer creatorId;
    @TableField(exist = false)
    @ApiModelProperty(value = "创建者名称",hidden = true)
    private String creatorName;
    @ApiModelProperty(value = "修改者id",hidden = true)
    private Integer updaterId;
    @TableField(exist = false)
    @ApiModelProperty(value = "修改者名称",hidden = true)
    private String updaterName;

    /**
     * 子列表
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "子列表",hidden = true)
    private List<TbUserPO> children;

}
