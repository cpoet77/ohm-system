// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/04.
package cs.ohms.subsystem.to;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
@Accessors(chain = true)
@ExcelModel(sheetName = "学生信息")
public class StudentInfoTo implements Serializable {
    @ExcelColumn(index = 0, title = "学院")
    private String collegeName;

    @ExcelColumn(index = 1, title = "专业")
    private String majorName;

    @ExcelColumn(index = 2, title = "班级")
    private String className;

    @ExcelColumn(index = 3, title = "学号")
    private String studentId;

    @ExcelColumn(index = 4, title = "姓名")
    private String realName;

    @ExcelColumn(index = 5, title = "性别")
    private String sex;

    @ExcelColumn(index = 6, title = "邮箱地址")
    private String email;

    @ExcelColumn(index = 7, title = "手机号")
    private String phone;
}
