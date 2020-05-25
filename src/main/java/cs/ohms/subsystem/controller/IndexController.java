// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/28.
package cs.ohms.subsystem.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping({"/", "/index.html"})
@RequiresAuthentication
public class IndexController {
    @GetMapping
    public String index(){
        return "index";
    }
}
