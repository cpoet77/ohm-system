package cn.ohms.subsystem.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 作业类
 *
 * @author shc
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_homework")
@DynamicInsert
@DynamicUpdate
public class HomeworkEntity implements Serializable {
    @Id
    @Column
    private Integer id; // 作业id

    @ManyToOne
    @JoinColumn(name = "course_group_id", referencedColumnName = "id")
    private CourseGroupEntity courseGroup; // 课群

    @Column(nullable = false)
    private String title; // 作业题目

    @Column
    private String content;  // 内容描述

    @OneToMany(mappedBy = "homework")
    private Set<PushHomeworkEntity> pushHomework = new HashSet<>(); // 以上传的作业

    @ManyToMany
    @JoinTable(name = "ohms_homework_resource", joinColumns = {@JoinColumn(name = "homework_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id")})
    private Set<ResourceEntity> resources = new HashSet<>();

    @Column(name = "start_time")
    private LocalDateTime startTime;  // 开始时间

    @Column(name = "end_time")
    private LocalDateTime endTime;    // 结束时间
}
