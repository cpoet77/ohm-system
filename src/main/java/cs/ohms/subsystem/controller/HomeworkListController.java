package cs.ohms.subsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2020/6/4 17:06
 *
 * @author LRC
 **/
@Controller
@RequestMapping("/homeworkList")
public class HomeworkListController {

    public HomeworkListController() {
    }

    @GetMapping
    public String index(){ return "pages/homeworkList"; }
}
