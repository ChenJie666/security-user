package oauth2.config;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import oauth2.utils.CommonResult;
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
@Slf4j
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
    public CommonResult<String> feignException(FeignException feignException) {
        int status = feignException.status();
        if (status >= 500) {
            log.error("feignClient调用异常",feignException);
        }
        String message = feignException.getMessage();

        return CommonResult.error(status, message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<String> exception(Exception e){
        String message = e.getMessage();
        e.printStackTrace();

        return CommonResult.error(message);
    }

}
