package oauth2.service.impl;

import oauth2.dao.AdminMapper;
import oauth2.entities.TbUserPO;
import oauth2.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public TbUserPO getInfoById(Integer id) {
        return adminMapper.getInfoById(id);
    }

}
