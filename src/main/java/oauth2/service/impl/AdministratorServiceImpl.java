package oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.AdministratorMapper;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.po.TbUserPO;
import oauth2.service.AdministratorService;
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
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, TbUserPO> implements AdministratorService {

    @Override
    public ObjListPO<TbUserPO> findAllUsers(Integer pageCurrent, Integer pageSize) {
        QueryWrapper<TbUserPO> tbUserPOQueryWrapper = new QueryWrapper<>();
        IPage<TbUserPO> tbUserPOIPage = baseMapper.selectPage(new Page<>(pageCurrent, pageSize), tbUserPOQueryWrapper);

        Assert.notEmpty(tbUserPOIPage.getRecords(), HttpStatus.NOT_FOUND.toString());

        return new ObjListPO<>(tbUserPOIPage.getCurrent(), tbUserPOIPage.getSize(), tbUserPOIPage.getPages(), tbUserPOIPage.getTotal(), tbUserPOIPage.getRecords());
    }

    @Override
    public List<String> findAllUserNames() {
        QueryWrapper<TbUserPO> tbUserPOQueryWrapper = new QueryWrapper<>();
        List<TbUserPO> users = baseMapper.selectList(tbUserPOQueryWrapper.select("username"));

        Assert.notEmpty(users, HttpStatus.NOT_FOUND.toString());

        return users.stream().map(TbUserPO::getUsername).collect(Collectors.toList());
    }

    @Override
    public TbUserPO findUserByName(String username) {
        QueryWrapper<TbUserPO> tbUserPOQueryWrapper = new QueryWrapper<>();
        tbUserPOQueryWrapper.eq("username", username);
        TbUserPO tbUserPO = baseMapper.selectOne(tbUserPOQueryWrapper);

        Assert.notNull(tbUserPO, HttpStatus.NOT_FOUND.toString());

        return tbUserPO;
    }

    @Override
    public TbUserPO findUserById(Integer id) {
        TbUserPO tbUserPO = baseMapper.selectById(id);

        Assert.notNull(tbUserPO, HttpStatus.NOT_FOUND.toString());

        return tbUserPO;
    }

    @Override
    public List<TbUserPO> findUsersByFactor(SearchFactorVO searchFactorVO) {
        // TODO

        return null;
    }

    /**
     * 添加
     */
    @Override
    public void addAdministrator(TbUserPO tbAdministratorPO) {
        int insert = baseMapper.insert(tbAdministratorPO);
        Assert.isTrue(insert > 0, "添加管理员失败");
    }

    /**
     * 更新
     */
    @Override
    public void updateAdministrator(TbUserPO tbAdministratorPO) {
        //不能修改用户的有效性
        tbAdministratorPO.setAccountNonExpired(null).setAccountNonLocked(null).setCredentialsNonExpired(null).setEnabled(null);
        int update = baseMapper.updateById(tbAdministratorPO);
        Assert.isTrue(update > 0, "更新管理员失败");
    }

    @Override
    public void enableAdministrator(Integer userId) {
        TbUserPO tbUserPO = baseMapper.selectById(userId);
        Boolean enabled = tbUserPO.getEnabled();
        int update = baseMapper.updateById(new TbUserPO().setId(userId).setEnabled(!enabled));
        Assert.isTrue(update > 0,"管理员有效性更改失败");
    }

    /**
     * 删除
     */
    @Override
    public void deleteAdministrator(Integer administratorId) {
        int delete = baseMapper.deleteById(administratorId);
        Assert.isTrue(delete > 0, "删除管理员失败");
    }

}
