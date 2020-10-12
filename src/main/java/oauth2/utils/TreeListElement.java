package oauth2.utils;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/12 13:39
 */
public interface TreeListElement<T> {

    Integer getId();

    Integer getParentId();

    List<T> getChildren();

}
