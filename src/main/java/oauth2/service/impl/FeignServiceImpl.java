package oauth2.service.impl;

import lombok.extern.slf4j.Slf4j;
import oauth2.dao.FeignMapper;
import oauth2.entities.TbUserPO;
import oauth2.service.FeignService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/23 11:28
 */
@Service
public class FeignServiceImpl implements FeignService {

    @Resource
    private FeignMapper feignMapper;

    /**
     * 以下是认证中心远程调用的接口
     * @return
     */
    @Override
    public TbUserPO getTbUser(String username){
        return feignMapper.findByUsername(username);
    }

    @Override
    public List<String> getRoleCodes(String username){
        return feignMapper.findRoleByUsername(username);
    }

    @Override
    public List<String> getAuthorities(List<String> roleCodes){
        return feignMapper.findAuthorityByRoleCodes(roleCodes);
    }

}
