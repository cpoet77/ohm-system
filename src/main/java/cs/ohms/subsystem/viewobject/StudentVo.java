package cs.ohms.subsystem.viewobject;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 2020/5/30 17:07
 *
 * @author _Struggler
 */
@Data
@Accessors(chain = true)
public class StudentVo {
    private Integer id;//用户id

    private String studentId;//学号

    private String name;//用户名

    private String realName;//姓名

    private Character sex; //性别

    private String className;//班级名

    private Integer classId;//班级id

    private Integer collegeId;//学院id

    private String collegeName;//学院名

    private Integer majorId; //专业id

    private String majorName;//专业名

}
