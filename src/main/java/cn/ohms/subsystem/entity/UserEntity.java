// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/21.
package cn.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    protected Integer id;//id

    @Column(nullable = false)
    protected String name;//姓名

    @Column(nullable = false)
    protected String password;//加密过的密码

    @Column(nullable = false)
    protected String salt;//加密用的盐

    @Column(nullable = false)
    protected Character sex;//性别

    @Column(nullable = false)
    protected String avatar;//头像地址

    @Column
    protected String email;//邮箱地址

    @Column
    protected String phone;//手机号

    @OneToOne(mappedBy = "user")
    private StudentEntity student;

    @OneToOne(mappedBy = "user")
    private TeacherEntity teacher;

    @ManyToMany
    @JoinTable(name = "ohms_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)}
            , inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)})
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<LoginRecordEntity> loginRecords = new HashSet<>();//登录记录
}
