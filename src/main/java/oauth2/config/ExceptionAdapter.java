package oauth2.config;

import oauth2.entities.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/16 11:28
 */
@ControllerAdvice
public class ExceptionAdapter {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<String> exception(IllegalArgumentException e){
        String message = e.getMessage();

        if(HttpStatus.NOT_FOUND.toString().equals(message)) {
            return CommonResult.error(HttpStatus.NOT_FOUND.value(), "未查询到数据");
        }

        e.printStackTrace();
        return CommonResult.error(message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<String> exception(Exception e){
        String message = e.getMessage();
        e.printStackTrace();

        return CommonResult.error(message);
    }

}
