package oauth2.entities.po;

import javassist.SerialVersionUID;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 13:20
 */
@Data
@AllArgsConstructor
public class ObjListPO<T> implements Serializable {
    private static final long SerialVersionUID = Long.MIN_VALUE;

    private long pageCurrent;

    private long pageSize;

    private long pages;

    private long total;

    private List<T> records;

}
