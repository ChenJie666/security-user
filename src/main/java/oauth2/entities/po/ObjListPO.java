package oauth2.entities.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 13:20
 */
@Data
@AllArgsConstructor
public class ObjListPO<T> {

    private long pageCurrent;

    private long pageSize;

    private long pages;

    private long total;

    private List<T> records;

}
