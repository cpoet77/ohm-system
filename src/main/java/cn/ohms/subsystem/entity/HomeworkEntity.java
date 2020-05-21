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

    @Column(nullable = false)
    private String title; // 作业题目

    @Column
    private String content;  // 内容描述


    @Column(name = "start_time")
    private LocalDateTime startTime;  // 开始时间

    @Column(name = "end_time")
    private LocalDateTime endTime;    // 结束时间
}
