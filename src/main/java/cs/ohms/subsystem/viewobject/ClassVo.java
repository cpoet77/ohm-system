// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/31.
package cs.ohms.subsystem.viewobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
@Accessors(chain = true)
public class ClassVo implements Serializable {
    private Integer id;//班级id

    private Integer collegeId;//学院id

    private String collegeName;//学院名

    private Integer majorId;//专业id

    private String majorName;//专业名

    private String name;//班级名

    private Long countStudent;//班级人数

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime datetime;//导入时间
}
