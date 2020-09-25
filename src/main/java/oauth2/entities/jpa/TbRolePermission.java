package oauth2.entities.jpa;

import javax.persistence.*;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/7/23 14:48
 */
@Entity
public class TbRolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long roleId;
    @Column(nullable = false)
    private Long permissionId;

}
