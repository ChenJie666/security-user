package oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.ClientMapper;
import oauth2.entities.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbClientPO;
import oauth2.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 10:27
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper,TbClientPO> implements ClientService {

    @Override
    public ObjListPO<TbClientPO> findAllClients(Integer pageCurrent,Integer pageSize) {
        QueryWrapper<TbClientPO> tbClientPOQueryWrapper = new QueryWrapper<>();
        IPage<TbClientPO> tbClientPOIPage = baseMapper.selectPage(new Page<>(pageCurrent, pageSize), tbClientPOQueryWrapper);

        Assert.notEmpty(tbClientPOIPage.getRecords(), HttpStatus.NOT_FOUND.toString());

        return new ObjListPO<>(tbClientPOIPage.getCurrent(), tbClientPOIPage.getSize(), tbClientPOIPage.getPages(), tbClientPOIPage.getTotal(), tbClientPOIPage.getRecords());
    }

    @Override
    public List<String> findAllClientNames() {
        QueryWrapper<TbClientPO> tbClientPOQueryWrapper = new QueryWrapper<>();
        List<TbClientPO> clients = baseMapper.selectList(tbClientPOQueryWrapper.select("name"));

        Assert.notEmpty(clients,HttpStatus.NOT_FOUND.toString());

        return clients.stream().map(TbClientPO::getAdditionalInformation).collect(Collectors.toList());
    }

    @Override
    public TbClientPO findClientByName(String clientId) {
        QueryWrapper<TbClientPO> tbClientPOQueryWrapper = new QueryWrapper<>();
        tbClientPOQueryWrapper.eq("name", clientId);
        TbClientPO tbClientPO = baseMapper.selectOne(tbClientPOQueryWrapper);

        Assert.notNull(tbClientPO, HttpStatus.NOT_FOUND.toString());

        return tbClientPO;
    }

    @Override
    public List<TbClientPO> findClientsByFactor(SearchFactorVO searchFactorVO) {
        return null;
    }
}
