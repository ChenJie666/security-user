package oauth2.controller;

import cn.hutool.json.JSONObject;
import oauth2.entities.CommonResult;
import oauth2.feign.HifunFeign;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 获取用户信息的接口
 * @Author: CJ
 * @Data: 2020/9/10 17:42
 */
@RestController
public class UserController {

    @Resource
    private HifunFeign hifunFeign;

    /**
     * 获取当前登录用户信息
     * @return
     */
    @GetMapping("/user/getUserInfo")
    public CommonResult<String> getUserInfo(){
        System.out.println("进入USER-CENTER");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userInfo = hifunFeign.getUserInfo(Integer.parseInt(principal.toString()));
        return CommonResult.success(userInfo);
    }

    /**
     * 获取当前登录用户手机号
     * @return
     */
    @GetMapping("/user/getMobile")
    public CommonResult<String> getMobile(){
        System.out.println("进入USER-CENTER");
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        String userInfo = hifunFeign.getUserInfo(Integer.parseInt(id));
        String mobile = new JSONObject(userInfo).getStr("mobile");
        return CommonResult.success(mobile);
    }

    /**
     * 获取当前用户的id
     * @return
     */
    @GetMapping("/user/getUserId")
    public CommonResult<Integer> getUserId(){
        System.out.println("进入USER-CENTER");
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        return CommonResult.success(Integer.parseInt(id));
    }

}
