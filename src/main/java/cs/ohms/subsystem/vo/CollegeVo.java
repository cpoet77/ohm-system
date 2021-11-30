// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/25.
package cs.ohms.subsystem.vo;

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
public class CollegeVo implements Serializable {
    private Integer id;//学院id

    private String name;//学院名

    private String description;//学院描述

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime datetime;//创建时间

    private Long countMajor;//专业统计

    private Long countStudent;//学生统计
}
