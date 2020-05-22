// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cn.ohms.subsystem.tableobject;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Data;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
public class TeacherTo {
    @ExcelColumn(title = "教职工号", index = 0)
    private String teacherId;

    @ExcelColumn(title = "姓名", index = 1)
    private String realName;//姓名

    @ExcelColumn(title = "密码", index = 2)
    private String password;//密码

    @ExcelColumn(title = "性别", index = 3)
    private String sex;
}
