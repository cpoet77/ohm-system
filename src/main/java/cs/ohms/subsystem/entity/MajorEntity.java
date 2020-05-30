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
 * Major Entity
 *
 * @author shc
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
@Table(name = "ohms_major")
public class MajorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    private String name;//专业名

    @Column(nullable = false)
    private LocalDateTime datetime;//导入时间

    @OneToMany(mappedBy = "major")
    private Set<ClassEntity> clazzs = new HashSet<>();//专业下的班级

    @ManyToOne
    @JoinColumn(name = "college_id", referencedColumnName = "id", nullable = false)
    private CollegeEntity college;//所属学院
}
