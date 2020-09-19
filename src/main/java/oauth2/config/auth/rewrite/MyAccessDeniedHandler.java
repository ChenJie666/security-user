package oauth2.config.auth.rewrite;

import com.fasterxml.jackson.databind.ObjectMapper;
import oauth2.entities.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/19 10:46
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            CommonResult<String> result = CommonResult.error(HttpStatus.FORBIDDEN.value(),"权限不足");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
