package cn.ohms.subsystem.tableobject;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Data;

/**
 * 2020/5/23 13:39
 *
 * @author LRC
 **/
@Data
public class MajorTo {
    @ExcelColumn(title = "学院",index = 0)
    private String college;

    @ExcelColumn(title = "专业名", index = 1)
    private String name;
}
