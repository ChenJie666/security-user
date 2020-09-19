package oauth2.config.auth.rewrite;

import com.fasterxml.jackson.databind.ObjectMapper;
import oauth2.entities.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/19 10:44
 */
@Component
public class MyAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable cause = authException.getCause();

        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        CommonResult<String> result = null;
        try {
            if(cause instanceof InvalidTokenException) {
                result = CommonResult.error(HttpStatus.UNAUTHORIZED.value(),"认证失败,无效或过期token");
            }else{
                result = CommonResult.error(HttpStatus.UNAUTHORIZED.value(),"认证失败,没有携带token");
            }
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
