// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/04.
package cs.ohms.subsystem.tableobject;

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
@ExcelModel(sheetName = "教师信息")
public class TeacherInfoTo implements Serializable {
    @ExcelColumn(index = 0, title = "教职工号")
    private String teacherId;

    @ExcelColumn(index = 1, title = "姓名")
    private String realName;

    @ExcelColumn(index = 2, title = "性别")
    private String sex;

    @ExcelColumn(index = 3, title = "邮箱地址")
    private String email;

    @ExcelColumn(index = 4, title = "手机号")
    private String phone;
}
