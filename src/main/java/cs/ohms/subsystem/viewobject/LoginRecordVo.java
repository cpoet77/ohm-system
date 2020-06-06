// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/05.
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
public class LoginRecordVo implements Serializable {
    private Long id;//记录id

    private Integer userId;//用户id

    private String userName;//用户名

    private String loginIp;//登录ip

    @JsonFormat(pattern = "yyyy年MM月dd日 hh:mm:ss")
    private LocalDateTime datetime;//登录时间

    private String province;//省

    private String provinceCode;//省区代码

    private String city;//城市

    private String cityCode;//城市代码

    private String address;//地址

    private String agent;
}
