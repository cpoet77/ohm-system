package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

/**
 * 2020/5/22 14:08
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/teachingSecretary/studentManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"})
@Slf4j
public class StudentManagementController {
    private StudentService studentService;
    private ResourceService resourceService;

    @Autowired
    public StudentManagementController(StudentService studentService, ResourceService resourceService) {
        this.studentService = studentService;
        this.resourceService = resourceService;
    }

    /**
     * 学生管理
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("pages/admin/studentManagement");
        return view.addObject("students", studentService.findVoAll());
    }

    /**
     * 导入学生信息
     *
     * @param studentXls studentXls
     * @return ResponseResult
     */
    @PostMapping("/importStudentInfo")
    @ResponseBody
    public ResponseResult importStudentInfo(@RequestParam("studentXls") @NotNull MultipartFile studentXls) {
        if (resourceService.isDemandXlsFile(studentXls)) {
            try (InputStream inputStream = studentXls.getInputStream()) {
                return studentService.importStudentInfo(inputStream);
            } catch (IOException e) {
                log.error("获取io流失败", e);
            }
        }
        return ResponseResult.enFail();//返回错误信息
    }
}
