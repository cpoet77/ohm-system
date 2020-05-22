package cn.ohms.subsystem.controller.teacher;

import cn.ohms.subsystem.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author LRC
 *
 **/

@Controller
@RequestMapping("/teachingSecretary/majorManagement")
public class MajorManagementController {
    private MajorService majorService;

    @Autowired
    public MajorManagementController(MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("/pages/majorManagement");
        return view.addObject("majors",majorService.findAll());
    }
}
