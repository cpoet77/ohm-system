// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/28.
package cs.ohms.subsystem.controller.common;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.service.LoginService;
import cs.ohms.subsystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/common/loginRecord")
@RequiresAuthentication
public class LoginRecordController {
    private LoginService loginService;

    @Autowired
    public LoginRecordController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 显示历史记录
     *
     * @return view
     */
    @GetMapping
    public String index() {
        return "pages/userLoginRecord";
    }

    /**
     * 分页获取自己的登录日志
     *
     * @param page 页码
     * @param size 每一页的数量
     * @return ResponseResult
     */
    @PostMapping("/loginInfoList")
    @ResponseBody
    public ResponseResult loginInfoList(@RequestParam("page") @NotNull @Min(0) Integer page,
                                        @RequestParam("size") @NotNull @Min(10) Integer size) {
        return (loginService.getLoginInfoListByPage((UserEntity) SecurityUtils.getSubject().getSession()
                .getAttribute(UserService.USER_SELF), page, size));
    }
}
