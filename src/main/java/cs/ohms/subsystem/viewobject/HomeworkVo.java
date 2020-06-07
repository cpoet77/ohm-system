// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
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
public class HomeworkVo implements Serializable {
    private Integer id;//作业id

    private Integer courseGroupId;//属于课群id

    private String title;//标题

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime startTime;//开始时间

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime endTime;//结束时间

    private Boolean state;//状态

    private Long lastDay;//剩余天,注意结束时间是否有效，否则为null

    private Long lastHour;//剩余小时

    private Long lastMin;//剩余分钟

    private Long countStudent;//应交人数

    private Long countPush;//已交人数
}
