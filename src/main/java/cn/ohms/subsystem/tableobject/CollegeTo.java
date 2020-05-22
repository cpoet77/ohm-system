// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cn.ohms.subsystem.tableobject;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Data;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
public class CollegeTo {
    @ExcelColumn(title = "学院名", index = 0)
    private String name;

    @ExcelColumn(title = "介绍", index = 1)
    private String description;
}
