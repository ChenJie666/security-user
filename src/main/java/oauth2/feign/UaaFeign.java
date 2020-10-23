package oauth2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/8/15 11:51
 */
@FeignClient("SECURITY-UAA")
public interface UaaFeign {

    @GetMapping(path = "/feign/uaa/publicKey")
    String publicKey();

}
