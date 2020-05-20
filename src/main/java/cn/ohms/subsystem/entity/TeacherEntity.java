package cn.ohms.subsystem.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Teacher entity
 *
 * @author shc
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Entity
@Table(name = "ohms_teacher")
@DynamicInsert
@DynamicUpdate
public class TeacherEntity implements Serializable {
    @Id
    @Column(name = "user_id")
    private Integer userId; // 用户信息

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "teacher_id", nullable = false)
    private String teacherId; // 教职工号

    @Column
    private String school; // 学校名

    @OneToMany(mappedBy = "teacher")
    private Set<CourseGroupEntity> courseGroups; // 创建的课群s

    @Column(nullable = false)
    private Boolean authentication; // 教师身份是否已验证
}
