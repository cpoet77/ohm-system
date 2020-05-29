package cs.ohms.subsystem.viewobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 2020/5/27 23:06
 *
 * @author _Struggler
 */
@Data
@Accessors(chain = true)
public class CourseGroupVo {
    private Integer id;//课程id;

    private Integer teacherId;//教师id

    private String teacherRealName;//教师名


    private String courseGroupName;//课程名

    private String description;//课程描述

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime datetime;//创建课群时间

    private String state;//课群状态
}
