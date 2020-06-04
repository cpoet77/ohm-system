// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/05.
package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.LoginService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.utils.NStringUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/teachingSecretary/loginRecord")
@RequiresRoles(value = {"admin", "teachingSecretary"}, logical = Logical.OR)
@Validated
public class LoginRecordController {
    private LoginService loginService;
    private ResourceService resourceService;

    @Autowired
    public LoginRecordController(LoginService loginService, ResourceService resourceService) {
        this.loginService = loginService;
        this.resourceService = resourceService;
    }

    /**
     * 查看并管理登录日志
     *
     * @return view
     */
    @GetMapping
    public String index() {
        return "pages/admin/loginRecordView";
    }

    /**
     * 分页获取登录记录信息
     *
     * @param draw     获取次数
     * @param start    开始位置
     * @param length   数量
     * @param userName 用户名
     * @return ResponseResult
     */
    @PostMapping("/loginRecordList")
    @ResponseBody
    public ResponseResult loginRecordList(@RequestParam("draw") @NotNull @Min(1) Integer draw
            , @RequestParam("start") @NotNull @Min(0) Integer start
            , @RequestParam("length") @NotNull @Min(5) Integer length
            , @RequestParam("filterUserName") @Length(max = 48) String userName) {
        int page = resourceService.calculatePageNum(start, length);
        return NStringUtil.isEmpty(userName) ? loginService.getLoginRecordListForPage(page, length).add("draw", draw)
                : loginService.getLoginRecordListByUserNameIsLikeFoPage(userName, page, length).add("draw", draw);
    }

    /**
     * 根据id删除记录
     * <p><b>管理员权限</b></p>
     *
     * @param loginRecordId 登录记录id
     * @return ResponseResult
     */
    @PostMapping("/deleteLoginRecord")
    @ResponseBody
    @RequiresRoles(value = {"admin"})
    public ResponseResult deleteLoginRecord(@RequestParam("loginRecordId") @NotNull @Min(1) Long loginRecordId) {
        return loginService.deleteLoginRecord(loginRecordId) ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }
}
