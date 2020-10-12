package oauth2.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/12 13:33
 */
public class ListUtil {

    // 1.递归法
    public static <T extends TreeListElement<T>> List<T> buildTree(List<T> tList) {
        List<T> result = new ArrayList<>();
        for (T t:tList) {
            if (t.getParentId() == 0) {
                result.add(t);
                setChildren(tList, t);
            }
        }
        return result;
    }

    public static <T extends TreeListElement<T>> void setChildren(List<T> tList, T parent) {
        for (T t: tList) {
            if(parent.getId().equals(t.getParentId())){
                parent.getChildren().add(t);
            }
        }
        if (parent.getChildren().isEmpty()) {
            return;
        }
        for (T t: parent.getChildren()) {
            setChildren(tList, t);
        }
    }

}
