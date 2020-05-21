// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/21.
package cn.ohms.subsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name ="ohms_student")
public class StudentEntity extends UserEntity implements Serializable {
    @Id
    @Column(name = "student_id")
    private String studentId;//学号
}
