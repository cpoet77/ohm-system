// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/28.
package cn.ohms.subsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping({"/", "/index.html"})
public class IndexController {
    @GetMapping
    public String index(){
        return "index";
    }
}
