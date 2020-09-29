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
 * @Data: 2020/9/23 15:33
 */
@Data
@Accessors(chain = true)
@TableName("tb_role")
@ApiModel(value = "角色类")
public class TbRolePO {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id",hidden = true)
    private Integer id;
    @ApiModelProperty(value = "父id")
    private Integer parentId;
    @ApiModelProperty(value = "角色名")
    private String name;
    @ApiModelProperty(value = "标志名")
    private String enname;
    @ApiModelProperty(value = "描述信息")
    private String description;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date created;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date updated;

}
