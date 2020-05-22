package cn.ohms.subsystem.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LRC
 *
 **/

@Controller
@RequestMapping("/teachingSecretary/majorManagement")
public class MajorManagementController {
    @GetMapping
    public String index(){
        return "pages/majorManagement";
    }
}
