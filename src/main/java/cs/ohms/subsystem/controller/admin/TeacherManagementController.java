package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.TeacherService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.validation.annotation.NSCharCheck;
import cs.ohms.subsystem.validation.annotation.NSEmail;
import cs.ohms.subsystem.validation.annotation.NSPhone;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 2020/5/22 16:47
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/teachingSecretary/teacherManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"}, logical = Logical.OR)
@Validated
@Slf4j
public class TeacherManagementController {
    private TeacherService teacherService;
    private UserService userService;

    @Autowired
    public TeacherManagementController(TeacherService teacherService, UserService userService) {
        this.teacherService = teacherService;
        this.userService = userService;
    }

    /**
     * 教师管理
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("pages/admin/teacherManagement");
        return view.addObject("teachers");
    }

    /**
     * 获取教师列表
     *
     * @param draw            获取次数
     * @param start           开始位置
     * @param length          长度
     * @param findTeacherName 根据名称过滤
     * @return ResponseResult
     */
    @PostMapping("/teacherList")
    @ResponseBody
    public ResponseResult teacherList(@RequestParam("draw") @NotNull @Min(1) Integer draw
            , @RequestParam("start") @NotNull @Min(0) Integer start
            , @RequestParam("length") @NotNull @Min(5) Integer length
            , @RequestParam("findTeacherName") @Length(max = 5) String findTeacherName) {
        ResponseResult result = NStringUtil.isEmpty(findTeacherName) ? teacherService.findTeacherByPage(start, length)
                : teacherService.findTeacherByTeacherNameAndPage(start, length, findTeacherName);
        return result.add("draw", draw);
    }

    /**
     * 保存教师信息
     * <p>以用户id为判断标准，为null时为新增，否则更新信息</p>
     *
     * @param userId    用户id
     * @param teacherId 教职工号
     * @param realName  教师姓名
     * @param sex       教师性别
     * @param phone     手机号
     * @param email     邮箱地址
     * @return ResponseResult
     */
    @PostMapping("/saveTeacher")
    @ResponseBody
    public ResponseResult saveTeacher(@RequestParam("userId") @Min(1) Integer userId
            , @RequestParam("teacherId") @NotEmpty @Length(min = 6, max = 24) String teacherId
            , @RequestParam("realName") @NotEmpty @Length(min = 2, max = 5) String realName
            , @RequestParam("sex") @NSCharCheck(value = {'M', 'F'}, message = "性别不正确") Character sex
            , @RequestParam("phone") @NSPhone(message = "手机号格式错误") String phone
            , @RequestParam("email") @NSEmail(message = "邮箱格式错误") String email) {
        return (teacherService.saveTeacher(SecurityUtils.getSubject().hasRole(UserService.USER_ADMIN_ROLE), userId
                , teacherId, realName, sex, phone, email) ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }

    /**
     * 根据用户id删除教师的用户信息
     * <p><b>注意：数据表为级联构造，一旦删除将会删除与该教师所有相关信息</b></p>
     *
     * @param userId 用户id
     * @return ResponseResult
     */
    @PostMapping("/deleteTeacher")
    @ResponseBody
    public ResponseResult deleteTeacher(@RequestParam("userId") @NotNull @Min(1) Integer userId) {
        return (userService.deleteUserById(SecurityUtils.getSubject().hasRole(UserService.USER_ADMIN_ROLE), userId)
                ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }

    @PostMapping("/changeTeachingSecretaryRole")
    @ResponseBody
    @RequiresRoles({"admin"})
    public ResponseResult changeTeachingSecretaryRole(@RequestParam("userId") @NotNull @Min(1) Integer userId){
        return (teacherService.changeTeachingSecretaryRole(userId) ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }
}
