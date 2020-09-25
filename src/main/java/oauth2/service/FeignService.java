package oauth2.service;

import oauth2.entities.po.TbUserPO;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 11:28
 */
public interface FeignService {

    /**
     * 以下是认证中心远程调用的接口
     * @return
     */
    TbUserPO getTbUser(String username);

    List<String> getRoleCodes(String username);

    List<String> getAuthorities(List<String> roleCodes);

}
