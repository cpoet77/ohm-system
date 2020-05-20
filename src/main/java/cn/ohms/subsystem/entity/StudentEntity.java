package cn.ohms.subsystem.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 学生类
 *
 * @author shc
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_student")
@DynamicInsert
@DynamicUpdate
public class StudentEntity implements Serializable {
    @Id
    @Column(name = "user_id")
    private Integer userId; // 用户id

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "student_id", nullable = false)
    private String studentId; // 学生学号

    @Column
    private String school; // 学校

    @Column
    private String college; // 学院

    @Column
    private String specialty; // 专业

    @ManyToMany
    @JoinTable(name = "ohms_student_course_group", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "course_group_id", referencedColumnName = "id"))
    private Set<CourseGroupEntity> courseGroups;    // 我加入的课群

    @OneToMany(mappedBy = "student")
    private Set<PushHomeworkEntity> pushHomework = new HashSet<>(); //已上传的作业
}
