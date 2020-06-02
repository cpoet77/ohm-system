// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/02.
package cs.ohms.subsystem.viewobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
@Accessors(chain = true)
public class TeacherVo implements Serializable {
    private Integer id;//用户id

    private String username;//用户名

    private String teacherId;//教职工号

    private String realName;//姓名

    private Character sex;//性别

    private Integer countCourseGroup;//管理的课群数

    private String email;//邮箱地址

    private String phone;//手机号码

    private Boolean isTeachingSecretary;//是否是教学秘书
}
