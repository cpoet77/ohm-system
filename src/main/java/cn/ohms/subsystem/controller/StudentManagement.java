package cn.ohms.subsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2020/5/22 14:08
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/teacherSecretary/studentManagement")
public class StudentManagement {

    @GetMapping
    public String studentManagement(){
        return "pages/studentManagement";
    }
}
