package cs.ohms.subsystem.tableobject;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * 2020/5/23 11:49
 *
 * @author _Struggler
 */
@Data
public class StudentTo implements Serializable {
    @ExcelColumn(title = "学号",index = 0)
    private String studentId;//学生id

    @ExcelColumn(title = "姓名",index = 1)
    private String realName;//学生真实姓名

    @ExcelColumn(title = "密码",index = 2)
    private String password;//密码

    @ExcelColumn(title = "性别",index = 3)
    private String sex;//性别

    @ExcelColumn(title = "专业名",index = 4)
    private String major;//学生所属专业
}
