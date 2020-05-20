// The code file was created by xuxi (email:n1040044765@qq.com) on 2020/5/4.
package cn.ohms.subsystem.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * PushHomework实体类
 *
 * @author xuxi
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_push_homework")
@DynamicInsert
@DynamicUpdate
public class PushHomeworkEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id; // 上传的作业id

    @ManyToOne
    @JoinColumn(name = "homework_id", referencedColumnName = "id", nullable = false)
    private HomeworkEntity homework; // 作业

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private StudentEntity student; // 学生用户

    @Column(name = "push_time", nullable = false, insertable = false)
    private LocalDateTime pushTime; // 上传的时间

    @Column
    private String text; // 文本描述

    @ManyToMany
    @JoinTable(name = "ohms_push_homework_resource", joinColumns = {@JoinColumn(name = "push_homework_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id")})
    private Set<ResourceEntity> resources = new HashSet<>();//上传的作业附件
}
