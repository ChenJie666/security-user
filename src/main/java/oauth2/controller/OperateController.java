package oauth2.controller;

import io.swagger.annotations.Api;
import oauth2.entities.CommonResult;
import oauth2.entities.po.TbUserPO;
import oauth2.service.AdministratorService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/9 19:55
 */
@RestController
@Api(value = "用户操作",tags = {"用户操作"})
public class OperateController {

    @Resource
    private AdministratorService administratorService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 注册用户
     * @param tbUserPO
     * @return
     */
    @PostMapping("/uc/operate/register")
    public CommonResult<String> register(TbUserPO tbUserPO){
        administratorService.addAdministrator(tbUserPO);

        return CommonResult.success("注册成功");
    }

    /**
     * 注销用户(添加到过期名单中)
     * @return
     */
    @PostMapping("/uc/operate/logout")
    public CommonResult<String> logout(){
        String administratorId = SecurityContextHolder.getContext().getAuthentication().getName();
        // 将用户和时间添加到redis中
        rabbitTemplate.convertAndSend("ExpiredTokenExchange","expiredToken.addExpiredToken", Arrays.asList(administratorId,System.currentTimeMillis()/1000) + "");

        return CommonResult.success("注销成功");
    }

}
