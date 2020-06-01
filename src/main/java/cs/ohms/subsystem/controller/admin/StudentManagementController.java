package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2020/5/22 14:08
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/teachingSecretary/studentManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"})
@Validated
@Slf4j
public class StudentManagementController {
    private StudentService studentService;

    @Autowired
    public StudentManagementController(StudentService studentService) {
        this.studentService = studentService;
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
}
