// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/4.
package cn.ohms.subsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 *
 * @author shc
 */
@Entity
@Table(name = "ohms_role")
public class RoleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Byte id; //角色id

    @Column(nullable = false, unique = true)
    private String name; //角色名

    @Column
    private String description; //角色描述

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime datetime; //角色添加时间

    @JsonIgnoreProperties(value = {"roles"})
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>(); // 拥有该角色的用户

    @ManyToMany
    @JoinTable(name = "ohms_role_permission", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Set<PermissionEntity> permissions; // 拥有的权限
}
