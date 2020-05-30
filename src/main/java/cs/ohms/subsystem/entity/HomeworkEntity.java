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
@Entity
@Table(name = "ohms_homework")
@Getter
@Setter
@Accessors(chain = true)
@DynamicUpdate
@DynamicInsert
public class HomeworkEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;//作业id

    @ManyToOne
    @JoinColumn(name = "course_group_id", referencedColumnName = "id", nullable = false)
    private CourseGroupEntity courseGroup;//课群

    @Column(nullable = false)
    private String title;//作业标题

    @Column
    private String content;//内容

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;//开始时间

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;//结束时间

    @Column(nullable = false)
    private Boolean state;//状态

    @ManyToMany
    @JoinTable(name = "ohms_homework_resource", joinColumns = {@JoinColumn(name = "homework_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false)})
    private Set<ResourceEntity> resources = new HashSet<>();

    @OneToMany(mappedBy = "homework")
    private Set<PushHomeworkEntity> pushHomework = new HashSet<>();//已上传的作业
}
