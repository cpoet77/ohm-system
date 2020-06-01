// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/21.
package cs.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ohms_view_student")
@DynamicInsert
@DynamicUpdate
public class StudentEntity implements Serializable {
    @Id
    @Column(name = "student_id")
    private String studentId;//学号

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;//用户信息

    @ManyToMany(mappedBy = "students")
    private Set<CourseGroupEntity> courseGroups = new HashSet<>();//加入的课群

    @ManyToOne
    @JoinColumn(name = "college_id", referencedColumnName = "id")
    private CollegeEntity college;//所属学院

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private MajorEntity major;//专业

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    private ClassEntity clazz;//所属班级

    @OneToMany(mappedBy = "student")
    private Set<PushHomeworkEntity> pushHomework = new HashSet<>();//上传的作业
}
