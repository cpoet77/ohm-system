// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/26.
package cs.ohms.subsystem.viewobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
@Accessors(chain = true)
public class MajorVo {
    private Integer id;//专业id

    private Integer collegeId;//学院id

    private String college;//学院名

    private String name;//专业名

    private Long countStudents;//统计学习本专业的学生数量

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime datetime;//导入时间
}
