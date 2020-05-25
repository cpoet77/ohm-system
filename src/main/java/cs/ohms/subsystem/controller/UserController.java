// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/7.
package cs.ohms.subsystem.controller;

import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UserController
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/user")
@RequiresGuest
public class UserController {

}
