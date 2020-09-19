package oauth2.config;

import oauth2.entities.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/16 11:28
 */
@ControllerAdvice
public class ExceptionAdapter {

    @ExceptionHandler
    public CommonResult<String> exception(Exception e){
        String message = e.getMessage();
        e.printStackTrace();

        return CommonResult.error(message);
    }

}
