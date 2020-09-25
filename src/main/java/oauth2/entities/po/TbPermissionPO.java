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
 * @Data: 2020/9/23 15:33
 */
@Data
@Accessors(chain = true)
@TableName("tb_permission")
public class TbPermissionPO {

    @TableId
    private Long id;
    private Long parentId;
    private String name;
    private String enname;
    private String url;
    private String description;
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updated;

}
