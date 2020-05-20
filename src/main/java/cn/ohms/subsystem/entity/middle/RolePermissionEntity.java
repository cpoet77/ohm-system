// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/13.
package cn.ohms.subsystem.entity.middle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * RolePermissionEntity
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_role_permission")
public class RolePermissionEntity implements Serializable {
    @EmbeddedId
    private Key id;

    @Embeddable
    @MappedSuperclass
    public static class Key implements Serializable{
        @Column(name = "role_id", nullable = false)
        private Byte roleId;

        @Column(name = "permission_id", nullable = false)
        private Integer permissionId;
    }
}
