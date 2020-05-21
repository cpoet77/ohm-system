// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/21.
package cn.ohms.subsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
public class StudentEntity extends UserEntity implements Serializable {
    @Id
    @Column(name = "student_id")
    private String studentId;//学号
}
