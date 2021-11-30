// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/04.
package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.service.StudentService;
import cs.ohms.subsystem.service.TeacherService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.to.StudentInfoTo;
import cs.ohms.subsystem.to.TeacherInfoTo;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.utils.UUIDUtil;
import cs.ohms.subsystem.validation.annotation.NSCharCheck;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 导入用户数据
 * <p>导入学生数据的时候具有一定的权限判定，非admin不能新增学院</p>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/teachingSecretary/importUserData")
@RequiresRoles(value = {"admin", "teachingSecretary"}, logical = Logical.OR)
@Validated
public class ImportUserDataController {
    private ResourceService resourceService;
    private TeacherService teacherService;
    private StudentService studentService;

    @Autowired
    public ImportUserDataController(ResourceService resourceService, TeacherService teacherService, StudentService studentService) {
        this.resourceService = resourceService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    /**
     * 导入用户数据
     */
    @GetMapping
    public String index() {
        return "pages/admin/importUserData";
    }

    /**
     * 上传文件并导入数据
     *
     * @param file   上传的数据文件
     * @param target 导入目标
     * @return true|false
     */
    @PostMapping("/uploadXlsxAndImpostUserData")
    @ResponseBody
    public ResponseResult uploadXlsxAndImpostUserData(@RequestParam("importUserDataXlsx") @NotNull MultipartFile file
            , @RequestParam("target") @NotNull @NSCharCheck(value = {'S', 'T'}, message = "请确定要导入的信息类型") Character target) {
        if (!resourceService.isDemandXlsFile(file)) {
            return ResponseResult.enFail();
        }
        try (InputStream in = file.getInputStream()) {
            String UUID = UUIDUtil.uuid32pure();
            String filePath = NStringUtil.joint("{}/{}{}", new SimpleDateFormat("yyyy/MM/dd")
                    .format(new Date()), UUID, ".xlsx");
            File toFile = new File(NStringUtil.joint("{}/{}", ResourceService.commonFilePath, filePath));
            if (target.equals('S')) {
                List<StudentInfoTo> studentInfoTos = studentService.importStudentInfoForTable(SecurityUtils.getSubject()
                        .hasRole(UserService.USER_ADMIN_ROLE), in);
                if (studentInfoTos == null) {
                    return ResponseResult.enError();
                }
                if (studentInfoTos.isEmpty()) {
                    return ResponseResult.enSuccess();
                }
                resourceService.dataExportToTableFile(studentInfoTos, StudentInfoTo.class, toFile);
                return ResponseResult.enFail().add("url", filePath);
            }
            List<TeacherInfoTo> teacherInfoTos = teacherService.importTeacherInfoForTable(in);
            if (teacherInfoTos == null) {
                return ResponseResult.enError();
            }
            if (teacherInfoTos.isEmpty()) {
                return ResponseResult.enSuccess();
            }
            resourceService.dataExportToTableFile(teacherInfoTos, TeacherInfoTo.class, toFile);
            return ResponseResult.enFail().add("url", filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.enError();
    }
}
