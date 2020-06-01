package cs.ohms.subsystem.tableobject;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 2020/5/23 13:39
 *
 * @author LRC
 **/
@Data
@Accessors(chain = true)
public class MajorTo implements Serializable {
    @ExcelColumn(title = "学院",index = 0)
    private String college;

    @ExcelColumn(title = "专业名", index = 1)
    private String name;
}
