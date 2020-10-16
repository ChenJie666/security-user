package oauth2.entities.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/16 13:44
 */
@Data
@TableName("department")
@ApiModel(value = "层级类")
@Accessors(chain = true)
public class DepartmentPO {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id", hidden = true)
    private Integer id;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "层级", hidden = true)
    private String level;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updated;

    @ApiModelProperty(value = "创建人id", hidden = true)
    private Integer creatorId;
    @TableField(exist = false)
    @ApiModelProperty(value = "创建者名称",hidden = true)
    private String creatorName;
    @ApiModelProperty(value = "修改者id",hidden = true)
    private Integer updaterId;
    @TableField(exist = false)
    @ApiModelProperty(value = "修改者名称",hidden = true)
    private String updaterName;

    @TableField(exist = false)
    @ApiModelProperty(value = "下属部门", hidden = true)
    private List<DepartmentPO> departmentPOs;

    @TableField(exist = false)
    @ApiModelProperty(value = "下属人员", hidden = true)
    private List<TbUserPO> tbUserPOs;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否是分支", hidden = true)
    private Boolean isBranch;


}
