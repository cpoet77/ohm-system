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
 * PushHomeworkResourceEntity
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_push_homework_resource")
public class PushHomeworkResourceEntity implements Serializable {
    @EmbeddedId
    private Key id;//id

    @Embeddable
    @MappedSuperclass
    public static class Key implements Serializable{
        @Column(name = "posh_homework_id", nullable = false)
        private Integer pushHomeworkId;

        @Column(name = "resource_id", nullable = false)
        private String resourceId;
    }
}
