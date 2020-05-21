// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/11.
package cn.ohms.subsystem.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 有一个人，她拥有无限的权利。
 * <p>admin超级管理员</p>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/admin")
@RequiresRoles(value = {"admin"})
public class AdminController {
    @GetMapping
    public String index() {
        return "admin/index";
    }
}
