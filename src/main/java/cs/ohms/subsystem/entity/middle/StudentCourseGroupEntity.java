// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/13.
package cs.ohms.subsystem.entity.middle;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * StudentCourseGroupEntity
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "ohms_student_course_group")
public class StudentCourseGroupEntity implements Serializable {
    @EmbeddedId
    private Key id; // id

    @Column(name = "join_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime joinTime; // 加入时间

    @Data
    @Accessors(chain = true)
    @Embeddable
    @MappedSuperclass
    public static class Key implements Serializable {
        @Column(name = "user_id", nullable = false)
        private Integer userId; // 用户id

        @Column(name = "course_group_id", nullable = false)
        private String courseGroupId; // 课群id
    }
}
