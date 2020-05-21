package cn.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LRC
 * @date 2020-05-21 16:50
 **/
@Entity
@Table(name = "ohms_course_group")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class CourseGroupEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id; //课群id

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "user_id")
    private TeacherEntity teacher;  //课群老师

    @Column(name = "name")
    private String name;    //课群名

    @Column(name = "description")
    private String description; //课群介绍

    @Column(name = "create_time")
    private LocalDateTime createTime;   //课群创建时间

    @Column(name = "state")
    private Boolean state;  //课群状态
}
