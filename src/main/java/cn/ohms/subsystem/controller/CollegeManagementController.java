package cn.ohms.subsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LRC
 *
 **/

@Controller
@RequestMapping("/teachingSecretary/collegeManagement")
public class CollegeManagementController {
    @GetMapping
    public String index(){
        return "pages/collegeManagement";
    }

}