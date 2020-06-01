// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/30.
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
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "ohms_push_homework")
@DynamicInsert
@DynamicUpdate
public class PushHomeworkEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;//上传作业id

    @ManyToOne
    @JoinColumn(name = "homework_id", referencedColumnName = "id", nullable = false)
    private HomeworkEntity homework;//所属作业

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    private StudentEntity student;//上传的学生

    @Column(name = "push_time", nullable = false)
    private LocalDateTime pushTime;//上传时间

    @Column
    private String text;//文本描述

    @ManyToMany
    @JoinTable(name = "ohms_push_homework_resource", joinColumns = {@JoinColumn(name = "push_homework_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false)})
    private Set<ResourceEntity> resources = new HashSet<>();
}
