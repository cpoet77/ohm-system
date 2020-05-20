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
 * StudentCourseGroupEntity
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_student_course_group")
public class StudentCourseGroupEntity implements Serializable {
    @EmbeddedId
    private Key id; // id

    @Column(name = "join_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime joinTime; // 加入时间

    @Embeddable
    @MappedSuperclass
    public static class Key implements Serializable{
        @Column(name = "user_id", nullable = false)
        private Integer userId; // 用户id

        @Column(name = "course_group_id", nullable = false)
        private String courseGroupId; // 课群id
    }
}
