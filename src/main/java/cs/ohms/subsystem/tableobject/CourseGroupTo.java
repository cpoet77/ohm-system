package cs.ohms.subsystem.tableobject;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * 2020/5/23 23:44
 *
 * @author LRC
 **/
@Data
public class CourseGroupTo implements Serializable {
    @ExcelColumn(title = "教师", index = 0)
    private String teacher;

    @ExcelColumn(title = "课群名", index = 1)
    private String name;

    @ExcelColumn(title = "介绍", index = 2)
    private String description;
}
