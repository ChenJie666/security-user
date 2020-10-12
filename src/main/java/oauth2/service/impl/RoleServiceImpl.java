package oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.RoleMapper;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.po.TbRolePO;
import oauth2.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 10:28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, TbRolePO> implements RoleService {

    /**
     * 查询
     */
    @Override
    public ObjListPO<TbRolePO> findAllRoles(Integer pageCurrent, Integer pageSize) {
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        IPage<TbRolePO> tbRolePOIPage = baseMapper.selectPage(new Page<>(pageCurrent, pageSize), tbRolePOQueryWrapper);

        Assert.notEmpty(tbRolePOIPage.getRecords(), HttpStatus.NOT_FOUND.toString());

        return new ObjListPO<>(tbRolePOIPage.getCurrent(), tbRolePOIPage.getSize(), tbRolePOIPage.getPages(), tbRolePOIPage.getTotal(), tbRolePOIPage.getRecords());
    }

    @Override
    public TbRolePO findAllRoles(Integer roleId) {
        TbRolePO tbRolePO = baseMapper.selectById(roleId);
        Assert.notNull(tbRolePO, HttpStatus.NOT_FOUND.toString());

        // 调用递归
        findChildren(tbRolePO);

        return tbRolePO;
    }

    private void findChildren(TbRolePO tbRolePO) {
        System.out.println("*****findChildren:" + tbRolePO);
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        tbRolePOQueryWrapper.eq("parent_id", tbRolePO.getId()).ne("id",0);
        List<TbRolePO> tbRolePOs = baseMapper.selectList(tbRolePOQueryWrapper);
        if (!Objects.isNull(tbRolePOs) && !tbRolePOs.isEmpty()) {
            tbRolePOs.forEach(this::findChildren);
        } else {
            tbRolePOs = null;
        }
        tbRolePO.setChildren(tbRolePOs);
    }


    @Override
    public List<String> findAllRoleNames() {
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        List<TbRolePO> roles = baseMapper.selectList(tbRolePOQueryWrapper.select("name"));

        Assert.notEmpty(roles, HttpStatus.NOT_FOUND.toString());

        return roles.stream().map(TbRolePO::getName).collect(Collectors.toList());
    }

    @Override
    public TbRolePO findRoleByName(String rolename) {
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        tbRolePOQueryWrapper.eq("name", rolename);
        TbRolePO tbRolePO = baseMapper.selectOne(tbRolePOQueryWrapper);

        Assert.notNull(tbRolePO, HttpStatus.NOT_FOUND.toString());

        return tbRolePO;
    }

    @Override
    public TbRolePO findRoleById(Integer id) {
        TbRolePO tbRolePO = baseMapper.selectById(id);

        Assert.notNull(tbRolePO, HttpStatus.NOT_FOUND.toString());

        return tbRolePO;
    }

    @Override
    public List<TbRolePO> findRolesByParentId(Integer parentId) {
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        tbRolePOQueryWrapper.eq("parent_id", parentId);
        List<TbRolePO> TbRolePOs = baseMapper.selectList(tbRolePOQueryWrapper);

        Assert.notEmpty(TbRolePOs, HttpStatus.NOT_FOUND.toString());

        return TbRolePOs;
    }

    @Override
    public List<String> findRoleNamesByParentId(Integer parentId) {
        QueryWrapper<TbRolePO> tbRolePOQueryWrapper = new QueryWrapper<>();
        tbRolePOQueryWrapper.eq("parent_id", parentId);
        List<TbRolePO> tbRolePOS = baseMapper.selectList(tbRolePOQueryWrapper);

        Assert.notEmpty(tbRolePOS, HttpStatus.NOT_FOUND.toString());

        return tbRolePOS.stream().map(TbRolePO::getName).collect(Collectors.toList());
    }

    @Override
    public List<TbRolePO> findRolesByFactor(SearchFactorVO searchFactorVO) {
        return null;
    }

    /**
     * 查询
     */
    @Override
    public void addRole(TbRolePO tbRolePO) {
        int insert = baseMapper.insert(tbRolePO);
        Assert.isTrue(insert > 0, "添加角色失败");
    }

    /**
     * 更新
     */
    @Override
    public void updateRole(TbRolePO tbRolePO) {
        int update = baseMapper.updateById(tbRolePO);
        Assert.isTrue(update > 0, "更新角色失败");
    }

    /**
     * 删除
     */
    @Override
    public void deleteRole(Integer roleId) {
        int delete = baseMapper.deleteById(roleId);
        Assert.isTrue(delete > 0, "删除角色失败");
    }
}
