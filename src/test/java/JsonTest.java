import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import oauth2.entities.CommonResult;
import org.junit.Test;

import java.util.Map;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/30 18:52
 */
public class JsonTest {

    @Test
    public void test(){
        CommonResult<String> success = CommonResult.success("12345");
        String s = new JSONObject(success).toString();
        System.out.println(s);
    }

    @Test
    public void test1(){
        CommonResult<String> success = CommonResult.success("12345");
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(success);
        System.out.println(stringObjectMap);
    }

}
