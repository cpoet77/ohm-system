// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/05.
package cs.ohms.subsystem.viewobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
@Accessors(chain = true)
public class LogFileInfoVo implements Serializable {
    private String fileName;//文件名

    private Long size;//文件大小
}
