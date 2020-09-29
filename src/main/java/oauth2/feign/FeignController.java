package oauth2.feign;

import oauth2.entities.po.TbUserPO;
import oauth2.service.FeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 11:28
 */
@RestController
public class FeignController {

    @Resource
    private FeignService administratorService;

    /**
     * 以下是认证中心远程调用用户中心的接口，不设权限，需要在gateway拦截这些请求路径。
     * @return
     */
    @GetMapping(path = "/feign/user/getTbUser")
    public TbUserPO getTbUser(String username){
        return administratorService.getTbUser(username);
    }

    @GetMapping(path = "/feign/user/getRoleCodes")
    public List<String> getRoleCodes(String username){
        return administratorService.getRoleCodes(username);
    }

    @PostMapping(path = "/feign/user/getAuthorities")
    public List<String> getAuthorities(@RequestBody List<String> roleCodes){
        return administratorService.getAuthorities(roleCodes);
    }

}
