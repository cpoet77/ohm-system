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
 * @author LRC
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ohms_course_group")
@DynamicInsert
@DynamicUpdate
public class CourseGroupEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; //课群id

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private TeacherEntity teacher;  //课群老师

    @Column(name = "name")
    private String name;    //课群名

    @Column(name = "description")
    private String description; //课群介绍

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;//课群创建时间

    @Column(name = "state")
    private Boolean state;  //课群状态

    @ManyToMany
    @JoinTable(name = "ohms_student_course_group", joinColumns = {@JoinColumn(name = "course_group_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "student_id")})
    private Set<StudentEntity> students = new HashSet<>();//加入课群的学生
}
