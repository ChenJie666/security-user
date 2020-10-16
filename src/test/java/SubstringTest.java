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

}
