package oauth2.service.impl;

import oauth2.dao.AdministratorMapper;
import oauth2.entities.TbUserPO;
import oauth2.service.AdministratorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Resource
    private AdministratorMapper adminMapper;

    @Override
    public TbUserPO getInfoById(Integer id) {
        return adminMapper.getInfoById(id);
    }


    /**
     * 以下是认证中心远程调用的接口
     * @return
     */
    @Override
    public TbUserPO getTbUser(String username){
        System.out.println("***TbUserPO:" + adminMapper.findByUsername(username));
        return adminMapper.findByUsername(username);
    }

    @Override
    public List<String> getRoleCodes(String username){
        return adminMapper.findRoleByUsername(username);
    }

    @Override
    public List<String> getAuthorities(List<String> roleCodes){
        return adminMapper.findAuthorityByRoleCodes(roleCodes);
    }

}
