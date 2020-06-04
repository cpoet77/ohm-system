package cs.ohms.subsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2020/6/4 11:42
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/seeCourseGroup")
public class SeeCourseGroupController {

    @GetMapping
    public String index(){

        return "pages/seeCourseGroup";
    }
}
