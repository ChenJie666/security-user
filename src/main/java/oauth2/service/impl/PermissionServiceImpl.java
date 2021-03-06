package oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import oauth2.dao.AdministratorMapper;
import oauth2.dao.PermissionMapper;
import oauth2.entities.po.ObjListPO;
import oauth2.entities.SearchFactorVO;
import oauth2.entities.po.TbPermissionPO;
import oauth2.entities.po.TbUserPO;
import oauth2.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/24 10:28
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,TbPermissionPO> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

//    @Override
//    public ObjListPO<TbPermissionPO> findAllPermissions(Integer pageCurrent,Integer pageSize) {
//        QueryWrapper<TbPermissionPO> tbPermissionPOQueryWrapper = new QueryWrapper<>();
//        IPage<TbPermissionPO> tbPermissionPOIPage = baseMapper.selectPage(new Page<>(pageCurrent, pageSize), tbPermissionPOQueryWrapper);
//
//        Assert.notEmpty(tbPermissionPOIPage.getRecords(), HttpStatus.NOT_FOUND.toString());
//
//        return new ObjListPO<>(tbPermissionPOIPage.getCurrent(), tbPermissionPOIPage.getSize(), tbPermissionPOIPage.getPages(), tbPermissionPOIPage.getTotal(), tbPermissionPOIPage.getRecords());
//    }

    @Override
    public List<TbPermissionPO> findAllPermissions() {
        QueryWrapper<TbPermissionPO> tbPermissionPOQueryWrapper = new QueryWrapper<>();
        tbPermissionPOQueryWrapper.eq("parent_id", 0);
        List<TbPermissionPO> tbPermissionPOs = baseMapper.selectList(tbPermissionPOQueryWrapper);

        Assert.notEmpty(tbPermissionPOs, HttpStatus.NOT_FOUND.toString());

        tbPermissionPOs.forEach(this::findChildren);

        return tbPermissionPOs;
    }

    @Override
    public List<TbPermissionPO> findMyPermissions(Integer userId){
        List<TbPermissionPO> permissions = permissionMapper.getPermissions(userId);
        Assert.notEmpty(permissions, HttpStatus.NOT_FOUND.toString());
        permissions.forEach(this::findChildren);
        return permissions;
    }

    @Resource
    private AdministratorMapper administratorMapper;

    private void findChildren(TbPermissionPO tbPermissionPO){
        System.out.println("*****tbPermissionPO" + tbPermissionPO);
        QueryWrapper<TbPermissionPO> tbPermissionPOQueryWrapper = new QueryWrapper<>();
        tbPermissionPOQueryWrapper.eq("parent_id", tbPermissionPO.getId()).ne("id",0);
        List<TbPermissionPO> tbPermissionPOs = baseMapper.selectList(tbPermissionPOQueryWrapper);

        if (!Objects.isNull(tbPermissionPOs) && !tbPermissionPOs.isEmpty()) {
            tbPermissionPOs.forEach(this::findChildren);
        } else {
            tbPermissionPOs = null;
        }

        // 查询创建更新者的用户名
        if (!Objects.isNull(tbPermissionPO.getCreatorId())) {
            String creator = administratorMapper.getUsernameById(tbPermissionPO.getCreatorId());
            tbPermissionPO.setCreatorName(creator);

            if(!Objects.isNull(tbPermissionPO.getUpdaterId())) {
                if (tbPermissionPO.getCreatorId().equals(tbPermissionPO.getUpdaterId())) {
                    tbPermissionPO.setUpdaterName(creator);
                } else {
                    String updater = administratorMapper.getUsernameById(tbPermissionPO.getUpdaterId());
                    tbPermissionPO.setUpdaterName(updater);
                }
            }
        }

        tbPermissionPO.setChildren(tbPermissionPOs);
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

    /**
     * 添加
     */
    @Override
    public void addPermission(TbPermissionPO tbPermissionPO) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = Integer.parseInt(name);
        tbPermissionPO.setCreatorId(userId).setUpdaterId(userId);
        int insert = baseMapper.insert(tbPermissionPO);
        Assert.isTrue(insert>0,"权限添加失败");
    }

    /**
     * 修改
     */
    @Override
    public void updatePermission(TbPermissionPO tbPermissionPO) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = Integer.parseInt(name);
        tbPermissionPO.setUpdaterId(userId);
        int update = baseMapper.updateById(tbPermissionPO);
        Assert.isTrue(update>0,"权限修改失败");
    }

    /**
     * 删除
     */
    @Override
    public void deletePermission(Integer permissionId) {
        int delete = baseMapper.deleteById(permissionId);
        Assert.isTrue(delete>0,"权限删除失败");
    }
}
