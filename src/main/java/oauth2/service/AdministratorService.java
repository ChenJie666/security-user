package oauth2.service;

import oauth2.entities.TbUserPO;

import java.util.List;

public interface AdministratorService {

    TbUserPO getInfoById(Integer id);


    /**
     * 以下是认证中心远程调用的接口
     * @return
     */
    TbUserPO getTbUser(String username);

    List<String> getRoleCodes(String username);

    List<String> getAuthorities(List<String> roleCodes);

}
