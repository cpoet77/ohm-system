// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/13.
package cn.ohms.subsystem.entity.middle;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * UserRoleEntity
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Data
@Accessors(chain = true)
@Table(name = "ohms_user_role")
public class UserRoleEntity implements Serializable {
    @EmbeddedId
    private Key id;

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime datetime; // 赋予角色的时间

    @Embeddable
    @MappedSuperclass
    @Data
    @Accessors(chain = true)
    public static class Key implements Serializable {
        @Column(name = "user_id", nullable = false)
        private Integer userId;

        @Column(name = "role_id", nullable = false)
        private Byte roleId;
    }
}
