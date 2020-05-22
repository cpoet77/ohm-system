package cn.ohms.subsystem.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2020/5/22 16:47
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/teachingSecretary/teacherManagement")
public class TeacherManagementController {
    @GetMapping
    public String index(){
        return "pages/teacherManagement";
    }
}
