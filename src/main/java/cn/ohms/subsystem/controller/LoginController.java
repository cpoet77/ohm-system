// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/28.
package cn.ohms.subsystem.controller;

import cn.ohms.subsystem.common.ResponseResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * LoginController
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/login")
@Validated
public class LoginController {
    @GetMapping
    public ModelAndView index(@RequestParam(required = false, defaultValue = "/") String backUrl){
        return (new ModelAndView("login").addObject("backUrl", backUrl));
    }

    /**
     * 注销登录
     *
     * @return ResponseResult
     */
    @PostMapping("/doLogout")
    @ResponseBody
    @RequiresAuthentication
    public ResponseResult doLogout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseResult.enSuccess();
    }
}
