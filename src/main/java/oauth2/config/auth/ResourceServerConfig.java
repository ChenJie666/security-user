package oauth2.config.auth;

import oauth2.config.auth.rewrite.MyAccessDeniedHandler;
import oauth2.config.auth.rewrite.MyAuthExceptionEntryPoint;
import oauth2.config.auth.rewrite.MyTokenExtractor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

@Configuration
public class ResourceServerConfig {

    private static final String RESOURCE_ID = "res1";

    @Resource
    private TokenStore tokenStore;

    @Resource
    private MyAuthExceptionEntryPoint myAuthExceptionEntryPoint;

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Resource
    private MyTokenExtractor myTokenExtractor;

    @Configuration
    @EnableResourceServer
    public class UserServerConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID)
                    .tokenStore(tokenStore)
                    .stateless(true)
                    .tokenExtractor(myTokenExtractor)
                    .authenticationEntryPoint(myAuthExceptionEntryPoint)
                    .accessDeniedHandler(myAccessDeniedHandler);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
//                    .antMatchers("/order/**").access("#oauth2.hasScope('ROLE_ADMIN')");
                    .antMatchers("/user/**").hasAnyAuthority("hifun")/*access("#oauth2.hasScope('ROLE_USER')")*/
                    .antMatchers("/administrator/**").hasAnyAuthority("/users/");
        }
    }

}
