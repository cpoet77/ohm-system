// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/05.
package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.service.LoginService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.validation.annotation.NSCharCheck;
import cs.ohms.subsystem.validation.annotation.NSEmail;
import cs.ohms.subsystem.validation.annotation.NSPhone;
import cs.ohms.subsystem.vo.LoginRecordVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 个人资料中心
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/personalCenter")
@RequiresAuthentication
@Validated
public class PersonalCenterController {
    private UserService userService;
    private LoginService loginService;

    @Autowired
    public PersonalCenterController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    /**
     * 个人中心
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("pages/personalCenterView").addObject("userInfo", userService
                .findUserByName(((UserEntity) SecurityUtils.getSubject().getSession().getAttribute(UserService.USER_SELF)).getName()))
                .addObject("listenToMyGoodWords", userService.listenToMyGoodWords());
    }

    /**
     * 设置主题
     *
     * @param skinName 皮肤名
     * @return ResponseResult
     */
    @PostMapping("/setSkin")
    @ResponseBody
    public ResponseResult setSkin(@RequestParam("skinName") @NotEmpty String skinName) {
        return (userService.saveSkin((UserEntity) SecurityUtils.getSubject().getSession().getAttribute(UserService.USER_SELF)
                , skinName) ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }

    /**
     * 保存用户头像设置
     *
     * @param avatarUrl 头像url地址
     * @return ResponseResult
     */
    @PostMapping("/saveAvatar")
    @ResponseBody
    public ResponseResult saveAvatar(@RequestParam("avatarUrl") @NotEmpty String avatarUrl) {
        return (userService.saveAvatar((UserEntity) SecurityUtils.getSubject().getSession().getAttribute(UserService.USER_SELF)
                , avatarUrl) ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }

    /**
     * 加载历史记录
     *
     * @param page 页号
     * @param size 数量
     * @return ResponseResult
     */
    @PostMapping("/loginInfoList")
    @ResponseBody
    public ResponseResult loginInfoList(@RequestParam("page") @NotNull @Min(0) Integer page
            , @RequestParam("size") @NotNull @Min(5) Integer size) {
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getSession().getAttribute(UserService.USER_SELF);
        List<LoginRecordVo> loginRecordVos = loginService.getLoginRecordByUserForPage(user.getId(), page, size);
        return loginRecordVos == null ? ResponseResult.enFail() : ResponseResult.enSuccess().add("list", loginRecordVos)
                .add("size", loginRecordVos.size());
    }

    /**
     * 更新用户密码
     *
     * @param password           原密码
     * @param newPassword        新密码
     * @param confirmNewPassword 重复新密码
     * @return ResponseResult
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public ResponseResult changePassword(@RequestParam("password") @NotEmpty @Pattern(regexp = "^[a-zA-Z0-9_]{6,32}$") String password
            , @org.jetbrains.annotations.NotNull @RequestParam("newPassword") @NotEmpty @Pattern(regexp = "^[a-zA-Z0-9_]{6,32}$") String newPassword
            , @RequestParam("confirmNewPassword") @NotEmpty @Pattern(regexp = "^[a-zA-Z0-9_]{6,32}$") String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) {
            return ResponseResult.enFail();
        }
        return (userService.changePassword((UserEntity) SecurityUtils.getSubject().getSession()
                .getAttribute(UserService.USER_SELF), password, newPassword) ? ResponseResult.enSuccess()
                : ResponseResult.enFail());
    }

    /**
     * 更新用户信息
     *
     * @param email 邮箱地址
     * @param phone 手机号
     * @param sex   性别
     * @return ResponseResult
     */
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public ResponseResult updateUserInfo(@RequestParam("email") @NSEmail(message = "邮箱地址不正确") String email
            , @RequestParam("phone") @NSPhone(message = "手机号格式不正确") String phone
            , @RequestParam("sex") @NSCharCheck(value = {'M', 'F'}, message = "性别不正确") Character sex) {
        return (userService.updateUser((UserEntity) SecurityUtils.getSubject().getSession()
                .getAttribute(UserService.USER_SELF), email, phone, sex) ? ResponseResult.enSuccess()
                : ResponseResult.enFail());
    }
}
