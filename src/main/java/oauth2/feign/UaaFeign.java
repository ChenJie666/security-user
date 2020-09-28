package oauth2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/8/15 11:51
 */
@FeignClient("TEST-UAA-CENTER")
public interface UaaFeign {

//    @PostMapping(path = "/oauth/token", headers = "Authorization=${}")
//    String oauthToken(@RequestParam String client_id,
//                      @RequestParam String client_secret,
//                      @RequestParam String grant_type,
//                      @RequestParam String username,
//                      @RequestParam String password
//    );

    @GetMapping(path = "/uaa/publicKey")
    String publicKey();

}
