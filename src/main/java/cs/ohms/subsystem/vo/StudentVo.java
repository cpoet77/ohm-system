package cs.ohms.subsystem.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 2020/5/30 17:07
 *
 * @author _Struggler
 */
@Data
@Accessors(chain = true)
public class StudentVo implements Serializable {
    private Integer id;//用户id

    private String studentId;//学号

    private String name;//用户名

    private String realName;//姓名

    private Character sex; //性别

    private Integer classId;//班级id

    private String className;//班级名

    private Integer collegeId;//学院id

    private String collegeName;//学院名

    private Integer majorId; //专业id

    private String majorName;//专业名

    private String email;//邮箱

    private String phone;//手机号

    private Integer countJoinCourseGroup;//加入的课群数量
}
