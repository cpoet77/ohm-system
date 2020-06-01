// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/01.
package cs.ohms.subsystem.controller.admin;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台管理首页
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/teachingSecretary")
@RequiresRoles(value = {"admin", "teachingSecretary"})
public class AdminController {
    @GetMapping
    public String index() {
        return "pages/admin/welcome";
    }
}
