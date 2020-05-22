// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/28.
package cn.ohms.subsystem.controller;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.entity.UserEntity;
import cn.ohms.subsystem.service.LoginService;
import cn.ohms.subsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * LoginController
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/login")
@Validated
@Slf4j
public class LoginController {
    private UserService userService;
    private LoginService loginService;

    @Autowired
    public LoginController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    /**
     * 返回登录视图
     *
     * @param backUrl 登录后回调的地址
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView index(@RequestParam(required = false, defaultValue = "/") String backUrl) {
        return (new ModelAndView("login").addObject("backUrl", backUrl));
    }

    /**
     * 验证用户信息完成登录
     *
     * @param username username
     * @param password password
     * @param request  HttpServletRequest
     * @return ResponseResult
     */
    @PostMapping("/finishLogin")
    @ResponseBody
    @RequiresGuest
    public ResponseResult finishLogin(@RequestParam("uname") @NotEmpty String username
            , @RequestParam @NotEmpty @Pattern(regexp = "^[a-zA-Z0-9_]{6,20}$") String password
            , HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            System.out.println(1111);
            subject.login(token);
            /* 登录成功，获取用户信息 */
            UserEntity user = userService.findUserByName(username);
            if (user != null) {
                subject.getSession().setAttribute(UserService.USER_SELF, user);
                loginService.loginRecord(request, user);
                System.out.println(222);
                /* 验证完成，登录成功 */
                return ResponseResult.enSuccess();
            }
        } catch (AuthenticationException e) {/* 登录失败 */
            log.info("Login failed，username : {}", username);
        }
        return ResponseResult.enFail();
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
