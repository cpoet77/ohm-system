package cs.ohms.subsystem.viewobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 2020/5/27 23:06
 *
 * @author _Struggler
 */
@Data
@Accessors(chain = true)
public class CourseGroupVo implements Serializable {
    private Integer id;//课程id;

    private String courseGroupName;//课群名

    private Integer teacherId;//教师id

    private String teacherRealName;//教师姓名

    private Integer countStudent;//统计学生数

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime datetime;//创建课群的时间

    private Boolean state;//课群状态
}
