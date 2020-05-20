// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/24.
package cn.ohms.subsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity class
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_user")
@DynamicInsert
@DynamicUpdate
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;// 用户id

    @Column(unique = true, nullable = false)
    private String name; // 用户名

    @Column(name = "real_name", nullable = false)
    private String realName; // 真实姓名

    @Column(unique = true, nullable = false)
    private String email; // 邮箱地址

    @JsonIgnore
    @Column(nullable = false, length = 32)
    private String password; // 加密过的密码

    @JsonIgnore
    @Column(nullable = false, length = 32)
    private String salt; // 加密用的盐

    @Column(nullable = false)
    private Character sex; // 性别

    @Column
    private String avatar; // 头像地址

    @Column(nullable = false)
    private Integer activity; // 活跃度

    @Column(name = "sign_up_time", insertable = false, updatable = false)
    private LocalDateTime signUpTime; // 注册时间

    @Column
    private String locale; // 区域

    @Column(insertable = false)
    private Boolean activation; // 是否被激活

    @JsonIgnoreProperties(value = "users")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ohms_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<RoleEntity> roles = new HashSet<>(); // 用户所拥有的角色

    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private Set<MessageBoxEntity> messages = new HashSet<>(); // 我的消息啊

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<LoginRecordEntity> loginRecords = new HashSet<>();//登录记录
}
