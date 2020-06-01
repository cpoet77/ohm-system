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
@Table(name = "ohms_class")
@DynamicInsert
@DynamicUpdate
public class ClassEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;//班级id

    @Column
    private String name;//班级名

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id", nullable = false)
    private MajorEntity major;//所属专业

    @OneToMany(mappedBy = "clazz")
    private Set<StudentEntity> students = new HashSet<>();//本班下的所有学生

    @Column(nullable = false)
    private LocalDateTime datetime;//导入时间
}
