// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/21.
package cs.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * user entity
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ohms_user")
@DynamicUpdate
@DynamicInsert
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Integer id;//id

    @Column(unique = true, nullable = false)
    private String name;//用户名

    @Column(name = "real_name", nullable = false)
    private String realName;//姓名

    @Column(nullable = false)
    private String password;//加密过的密码

    @Column(nullable = false)
    private String salt;//加密用的盐

    @Column(nullable = false)
    private Character sex;//性别

    @Column(nullable = false)
    private String avatar;//头像地址

    @Column
    private String email;//邮箱地址

    @Column
    private String phone;//手机号

    @Column
    private String skin;//界面皮肤

    @OneToOne(mappedBy = "user")
    private StudentEntity student;//学生

    @OneToOne(mappedBy = "user")
    private TeacherEntity teacher;//教师

    @ManyToMany
    @JoinTable(name = "ohms_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)}
            , inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)})
    private Set<RoleEntity> roles = new HashSet<>();//拥有的角色

    @OneToMany(mappedBy = "user")
    private Set<LoginRecordEntity> loginRecords = new HashSet<>();//登录记录

    @OneToMany(mappedBy = "user")
    private Set<ResourceEntity> resources = new HashSet<>();//上传的记录

    @ManyToMany(mappedBy = "users")
    private Set<MessageBoxEntity> messageBox = new HashSet<>();//消息箱
}
