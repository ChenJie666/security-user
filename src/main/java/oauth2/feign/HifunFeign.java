package oauth2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/10 9:26
 */
@FeignClient(name = "hifun-feign", url = "http://192.168.32.234:44440")
//@FeignClient(name = "hifun-service-user")
public interface HifunFeign {

    /**
     * 验证密码
     * @param id
     * @param password
     * @return
     */
    @PostMapping(path = "/password/check", consumes = "application/json")
    String check(@RequestParam("id") Integer id, @RequestBody String password);

    /**
     * 根据手机号获取用户信息
     * @param mobile
     * @return
     */
    @GetMapping(path = "/user/mobile")
    String mobile(@RequestParam("mobile") String mobile);

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @GetMapping(path = "/user/{id}")
    String getUserInfo(@PathVariable("id") Integer id);


}
