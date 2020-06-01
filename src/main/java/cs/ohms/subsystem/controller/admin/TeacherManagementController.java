package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2020/5/22 16:47
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/teachingSecretary/teacherManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"})
@Validated
@Slf4j
public class TeacherManagementController {
    private TeacherService teacherService;

    @Autowired
    public TeacherManagementController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * 教师管理
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("pages/admin/teacherManagement");
        return view.addObject("teachers", teacherService.findAll());
    }
}
