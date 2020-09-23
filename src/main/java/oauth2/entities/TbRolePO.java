package oauth2.entities;

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
public class TbRolePO {

    private Integer id;

    private Integer parentId;

    private String name;

    private String enname;

    private String description;

    private Date created;

    private Date updated;

}
