import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/16 20:41
 */
public class SubstringTest {

    @Test
    public void test(){
        String level = "0.2.1.5";
        String substring = level.substring(0, level.lastIndexOf("."));
        System.out.println(substring);
    }

    @Test
    public void test1(){
        String str = "123";
        String str1 = "123 ";
        System.out.println(StringUtils.isNumeric(str));
        System.out.println(StringUtils.isNumeric(str1));
    }

}
