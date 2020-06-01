// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/31.
package cs.ohms.subsystem.tableobject;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
@Accessors(chain = true)
public class ClassTo implements Serializable {
    @ExcelColumn(index = 0, title = "学院名")
    private String collegeName;//学院名

    @ExcelColumn(index = 1, title = "专业名")
    private String majorName;//专业名

    @ExcelColumn(index = 2, title = "班级名")
    private String name;//班级名
}
