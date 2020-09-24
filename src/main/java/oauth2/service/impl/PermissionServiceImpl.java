package oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.PermissionMapper;
import oauth2.entities.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.TbPermissionPO;
import oauth2.entities.TbRolePO;
import oauth2.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 10:28
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,TbPermissionPO> implements PermissionService {

    @Override
    public ObjListPO<TbPermissionPO> findAllPermissions(Integer pageCurrent,Integer pageSize) {
        QueryWrapper<TbPermissionPO> tbPermissionPOQueryWrapper = new QueryWrapper<>();
        IPage<TbPermissionPO> tbPermissionPOIPage = baseMapper.selectPage(new Page<>(pageCurrent, pageSize), tbPermissionPOQueryWrapper);

        Assert.notEmpty(tbPermissionPOIPage.getRecords(), HttpStatus.NOT_FOUND.toString());

        return new ObjListPO<>(tbPermissionPOIPage.getCurrent(), tbPermissionPOIPage.getSize(), tbPermissionPOIPage.getPages(), tbPermissionPOIPage.getTotal(), tbPermissionPOIPage.getRecords());
    }

    @Override
    public List<String> findAllPermissionNames() {
        QueryWrapper<TbPermissionPO> tbPermissionPOQueryWrapper = new QueryWrapper<>();
        List<TbPermissionPO> permissions = baseMapper.selectList(tbPermissionPOQueryWrapper.select("name"));

        Assert.notEmpty(permissions,HttpStatus.NOT_FOUND.toString());

        return permissions.stream().map(TbPermissionPO::getName).collect(Collectors.toList());
    }

    @Override
    public TbPermissionPO findPermissionByName(String permissionname) {
        QueryWrapper<TbPermissionPO> tbPermissionPOQueryWrapper = new QueryWrapper<>();
        tbPermissionPOQueryWrapper.eq("name", permissionname);
        TbPermissionPO tbPermissionPO = baseMapper.selectOne(tbPermissionPOQueryWrapper);

        Assert.notNull(tbPermissionPO, HttpStatus.NOT_FOUND.toString());

        return tbPermissionPO;
    }

    @Override
    public TbPermissionPO findPermissionById(Integer id) {
        TbPermissionPO tbPermissionPO = baseMapper.selectById(id);

        Assert.notNull(tbPermissionPO, HttpStatus.NOT_FOUND.toString());

        return tbPermissionPO;
    }

    @Override
    public List<TbPermissionPO> findPermissionsByParentId(Integer parentId) {
        QueryWrapper<TbPermissionPO> tbPermissionPOQueryWrapper = new QueryWrapper<>();
        tbPermissionPOQueryWrapper.eq("parent_id", parentId);
        List<TbPermissionPO> tbPermissionPOS = baseMapper.selectList(tbPermissionPOQueryWrapper);

        Assert.notEmpty(tbPermissionPOS, HttpStatus.NOT_FOUND.toString());

        return tbPermissionPOS;
    }

    @Override
    public List<String> findPermissionNamesByParentId(Integer parentId) {
        QueryWrapper<TbPermissionPO> tbPermissionPOQueryWrapper = new QueryWrapper<>();
        tbPermissionPOQueryWrapper.eq("parent_id", parentId);
        List<TbPermissionPO> tbPermissionPOS = baseMapper.selectList(tbPermissionPOQueryWrapper);

        Assert.notEmpty(tbPermissionPOS, HttpStatus.NOT_FOUND.toString());

        return tbPermissionPOS.stream().map(TbPermissionPO::getName).collect(Collectors.toList());
    }

    @Override
    public List<TbPermissionPO> findPermissionsByFactor(SearchFactorVO searchFactorVO) {
        return null;
    }
}
