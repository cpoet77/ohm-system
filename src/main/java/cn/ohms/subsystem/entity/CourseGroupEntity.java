// The code file was created by xuxi (email:n1040044765@qq.com) on 2020/5/4.
package cn.ohms.subsystem.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * CourseGroup实体类
 *
 * @author xuxi
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_course_group")
@DynamicInsert
@DynamicUpdate
public class CourseGroupEntity implements Serializable {
    @Id
    @Column
    private String id; //课群id

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private TeacherEntity teacher; // 创建课群的教师

    @Column(nullable = false)
    private String name; //课群名

    @Column
    private String description; //课群介绍

    @Column
    private String cover; //课群封面地址

    @Column(name = "start_time")
    private LocalDateTime startTime; //课群创建时间

    @Column(name = "end_time")
    private LocalDateTime endTime; //课群结束时间

    @ManyToMany(mappedBy = "courseGroups")
    private Set<StudentEntity> students = new HashSet<>(); // 一群学生，ha-ha-ha....

    @OneToMany(mappedBy = "courseGroup")
    private Set<HomeworkEntity> homework = new HashSet<>();//课群已发布的作业

    @Column(nullable = false)
    private Boolean state; //课群状态
}
