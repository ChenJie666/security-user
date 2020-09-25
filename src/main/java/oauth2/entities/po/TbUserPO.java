package oauth2.entities.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class TbUserPO {

//    /**
//     * 用户名
//     */
//    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账户没有过期
     */
    private boolean accountNonExpired = true;
    /**
     * 账户没被锁定 （是否冻结）
     */
    private boolean accountNonLocked = true;
    /**
     * 密码没有过期
     */
    private boolean credentialsNonExpired = true;
    /**
     * 账户是否可用（是否被删除）
     */
    private boolean enabled = true;

    /*--------------------------------------------*/

    /**
     * 用户id
     */
    @TableId
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String created;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updated;

}
