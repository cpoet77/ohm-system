// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/21.
package cn.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "ohms_student")
public class StudentEntity implements Serializable {
    @Id
    @Column(name = "student_id")
    private String studentId;//学号

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @ManyToMany(mappedBy = "students")
    private Set<CourseGroupEntity> courseGroups = new HashSet<>();//加入的课群
}
