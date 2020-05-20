// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/28.
package cn.ohms.subsystem.controller;

import cs.ohmsubsystem.common.ResponseResult;
import cs.ohmsubsystem.entity.UserEntity;
import cs.ohmsubsystem.service.LoginService;
import cs.ohmsubsystem.service.UserService;
import cs.ohmsubsystem.utils.NStringUtil;
import cs.ohmsubsystem.validation.annotation.NSEmail;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class LoginController {
    private final static Logger log = LoggerFactory.getLogger(LoginController.class);
    private UserService userService;
    private LoginService loginService;

    @Autowired
    public LoginController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    /**
     * login page
     *
     * @return login page
     */
    @GetMapping
    public ModelAndView index(@RequestParam(required = false) @URL String backUrl) {
        ModelAndView view = new ModelAndView("login");
        String to = NStringUtil.isEmpty(backUrl) ? "/" : backUrl;
        if (SecurityUtils.getSubject().isAuthenticated()) {
            view.setViewName(NStringUtil.joint("redirect:{}", to));
            return view;
        }
        view.addObject("backUrl", to);
        return view;
    }

    /**
     * 验证用户信息完成登录
     *
     * @param email    email
     * @param password password
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ResponseResult
     */
    @PostMapping("/finishLogin")
    @ResponseBody
    @RequiresGuest
    public ResponseResult finishLogin(@RequestParam @NotEmpty @NSEmail String email
            , @RequestParam @NotEmpty @Pattern(regexp = "^[a-zA-Z0-9_]{6,20}$") String password
            , HttpServletRequest request
            , HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        try {
            UserEntity user = userService.getUserByEmail(email);
            if (user == null) {
                return ResponseResult.enFail();
            }
            subject.login(token);
            /* 登录成功，获取用户信息 */
            subject.getSession().setAttribute(UserService.USER_SELF, user);
            loginService.loginRecord(request, user.getId());
            /* 设置语言cookie */
            Cookie cookie = new Cookie(UserService.LANGUAGE_SELF_COOKIE, user.getLocale());
            cookie.setMaxAge(0x278D00);
            cookie.setPath("/");
            response.addCookie(cookie);
            /* 验证完成，登录成功 */
            return ResponseResult.enSuccess();
        } catch (AuthenticationException e) {/* 登录失败 */
            log.info("Login failed，email : {}", email);
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
