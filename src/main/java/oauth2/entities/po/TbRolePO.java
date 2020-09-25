package oauth2.entities.po;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("tb_role")
public class TbRolePO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    private String name;
    private String enname;
    private String description;
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updated;

}
