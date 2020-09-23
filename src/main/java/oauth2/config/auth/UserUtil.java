//package oauth2.config.auth;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//
///**
// * @Description:
// * @Author: CJ
// * @Data: 2020/9/23 9:36
// */
//public class UserUtil {
//
//    public static LoginAppUser getLoginAppUser(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof OAuth2Authentication) {
//            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
//            authentication = oAuth2Auth.getUserAuthentication();
//
//            if (authentication instanceof UsernamePasswordAuthenticationToken) {
//                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
//                Object details = authenticationToken.getDetails();
//            }
//        }
//        return null;
//    }
//
//}
