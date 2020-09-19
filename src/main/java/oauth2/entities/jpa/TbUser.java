package oauth2.entities.jpa;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/23 14:47
 */
@Entity
@Data
@Accessors(chain = true)
@TableName(value = "tb_user")
public class TbUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @TableId(type = IdType.ID_WORKER_STR)
    private Long id;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String phone;
    private String email;
    @Column(columnDefinition = "tinyint DEFAULT 1 COMMENT '账户没有过期'")
    private Boolean accountNonExpired;
    @Column(columnDefinition = "tinyint DEFAULT 1 COMMENT '用户没有被锁定'")
    private Boolean accountNonLocked;
    @Column(columnDefinition = "tinyint DEFAULT 1 COMMENT '凭证没有过期'")
    private Boolean credentialsNonExpired;
    @Column(columnDefinition = "tinyint DEFAULT 1 COMMENT '账户是否可用'")
    private Boolean enabled;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updated;
    @Transient
    @TableField(exist = false)
    private List<String> roles;

}
