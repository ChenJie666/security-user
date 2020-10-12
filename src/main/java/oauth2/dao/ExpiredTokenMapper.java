package oauth2.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/10 17:31
 */
@Component
public interface ExpiredTokenMapper {

    /**
     * 通过权限查询角色
     */
    @Select("<script> " +
            "SELECT DISTINCT rp.role_id " +
            "FROM tb_role_permission rp " +
            "WHERE rp.permission_id IN " +
            "<foreach collection='permissionIds' item='' open='(' separator=',' close=')'> " +
            "#{permissionId} " +
            "</foreach> " +
            "</script>")
    List<Integer> getRoleIdsByPermissionIds(@Param("permissionIds") List<Integer> permissionIds);

    /**
     * 通过角色查询用户
     */
    @Select("<script> " +
            "SELECT DISTINCT ur.id " +
            "FROM tb_user_role ur " +
            "WHERE ur.role_id IN " +
            "<foreach collection='roleIds' item='' open='(' separator=',' close=')'> " +
            "#{roleId} " +
            "</foreach> " +
            "</script>")
    List<Integer> getUserIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);

    /**
     * 通过用户角色中间表查询用户id
     */
    @Select("<script> " +
            "SELECT DISTINCT ur.id " +
            "FROM tb_user_role ur " +
            "WHERE ur.id IN " +
            "<foreach collection='bindIds' item='' open='(' separator=',' close=')'> " +
            "#{bindId} " +
            "</foreach> " +
            "</script>")
    List<Integer> getUserIdsByUserRoleIds(@Param("bindIds") List<Integer> bindIds);

    /**
     * 通过角色权限中间表查询角色id
     */
    @Select("<script> " +
            "SELECT DISTINCT rp.role_id " +
            "FROM tb_role_permission rp " +
            "WHERE rp.id IN " +
            "<foreach collection='bindIds' item='' open='(' separator=',' close=')'> " +
            "#{bindId} " +
            "</foreach> " +
            "</script>")
    List<Integer> getRoleIdsByRolePermissionIds(@Param("bindIds") List<Integer> bindIds);

}
