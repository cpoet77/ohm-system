// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/4.
package cs.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ohms_role")
@DynamicInsert
@DynamicUpdate
public class RoleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id; //角色id

    @Column(nullable = false, unique = true)
    private String name; //角色名

    @Column
    private String description; //角色描述

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime datetime; //角色添加时间

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();//获得该角色的所有用户
}
