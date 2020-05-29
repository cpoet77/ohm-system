// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/7.
package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

/**
 * UserController
 *
 * <p><b>基本管理，界面皮肤设置</b></p>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/user")
@RequiresAuthentication
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 个人资料页面
     *
     * @return view
     */
    @GetMapping("/myProfile")
    public String myProfile() {
        return "pages/myProfile";
    }

    /**
     * 主题中心
     *
     * @return view
     */
    @GetMapping("/interfaceThemeCenter")
    public String interfaceThemeCenter() {
        return "pages/interfaceThemeCenter";
    }

    /**
     * 设置皮肤
     *
     * @param skinName 皮肤名
     * @return ResponseResult
     */
    @PostMapping("/setSkin")
    @ResponseBody
    public ResponseResult setSkin(@RequestParam("skinName") @Pattern(
            regexp = "(blue)|(black)|(purple)|(green)|(red)|(yellow)|(blue-light)|(black-light)|(purple-light)|(green-light)|(red-light)|(yellow-light)"
    ) String skinName) {
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getSession().getAttribute(UserService.USER_SELF);
        return (userService.saveSkin(user, skinName) ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }
}
