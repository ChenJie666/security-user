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
@TableName("tb_permission")
@ApiModel(value = "权限类")
public class TbPermissionPO {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id",hidden = true)
    private Long id;
    @ApiModelProperty(value = "父id")
    private Long parentId;
    @ApiModelProperty(value = "权限名")
    private String name;
    @ApiModelProperty(value = "权限标志")
    private String enname;
    @ApiModelProperty(value = "路径")
    private String url;
    @ApiModelProperty(value = "描述")
    private String description;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date created;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date updated;

}
