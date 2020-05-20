// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/13.
package cn.ohms.subsystem.entity.middle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * UserRoleEntity
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_user_role")
public class UserRoleEntity implements Serializable {
    @EmbeddedId
    private Key id;

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime datetime; // 赋予角色的时间

    @Embeddable
    @MappedSuperclass
    public static class Key implements Serializable {
        @Column(name = "user_id", nullable = false)
        private Integer userId;
        @Column(name = "role_id", nullable = false)
        private Byte roleId;
    }
}
