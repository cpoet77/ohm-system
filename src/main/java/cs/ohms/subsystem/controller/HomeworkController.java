package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.exception.NSRuntimeException;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.HomeworkService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.viewobject.CourseGroupVo;
import cs.ohms.subsystem.viewobject.HomeworkVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 2020/6/4 17:06
 *
 * @author LRC
 **/
@Controller
@RequestMapping("/homework")
@RequiresAuthentication
@Validated
public class HomeworkController {
    private CourseGroupService courseGroupService;
    private HomeworkService homeworkService;

    @Autowired
    public HomeworkController(CourseGroupService courseGroupService, HomeworkService homeworkService) {
        this.courseGroupService = courseGroupService;
        this.homeworkService = homeworkService;
    }

    /**
     * 显示课群下的作业信息
     * <p>包括教师发布作业</p>
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index(@RequestParam("courseGroup") @NotNull @Min(1) Integer courseGroupId) {
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = (UserEntity) subject.getSession().getAttribute(UserService.USER_SELF);
        if (subject.hasRole(UserService.USER_ADMIN_ROLE) || subject.hasRole(UserService.USER_TEACHING_SECRETARY_ROLE)
                || subject.hasRole(UserService.USER_TEACHER_ROLE)) {/*教师*/
            CourseGroupVo courseGroup = courseGroupService.findByTeacherAndId(user, courseGroupId);
            if (courseGroup == null) {
                throw new NSRuntimeException("某位教师访问了不属于他的课群？至于是谁，不方便透露。");
            }
            return (new ModelAndView("pages/homeworkViewByTeacher").addObject("courseGroup"
                    , courseGroup));
        }
        CourseGroupVo courseGroup = courseGroupService.findByStudentAndId(user, courseGroupId);
        if (courseGroup == null) {
            throw new NSRuntimeException(NStringUtil.joint("学生用户(userId : {}) 非法访问课群，已经被拒绝！", user.getId()));
        }
        return (new ModelAndView("pages/homeworkViewByStudent").addObject("courseGroup"
                , courseGroup));
    }

    /**
     * 获取某课群下的作业信息
     *
     * @param courseGroupId 课群id
     * @param page          页号
     * @param size          每页数量
     * @param name          过滤名称
     * @param state         过滤状态
     * @return ResponseResult
     */
    @PostMapping("/homeworkList")
    @ResponseBody
    public ResponseResult homeworkList(@RequestParam("courseGroupId") @NotNull @Min(1) Integer courseGroupId
            , @RequestParam("page") @NotNull @Min(0) Integer page
            , @RequestParam("size") @NotNull @Min(8) Integer size
            , @RequestParam("name") String name
            , @RequestParam("state") @NotNull @Min(0) @Max(2) Integer state) {
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = (UserEntity) subject.getSession().getAttribute(UserService.USER_SELF);
        boolean isTeacher = subject.hasRole(UserService.USER_ADMIN_ROLE) || subject.hasRole(UserService.USER_TEACHING_SECRETARY_ROLE)
                || subject.hasRole(UserService.USER_TEACHER_ROLE);
        List<HomeworkVo> homeworkVos = NStringUtil.isEmpty(name) ? homeworkService.findByCourseGroupAndStateForPage(user
                , isTeacher, courseGroupId, state, page, size) : homeworkService.findByCourseGroupAndNameIsLikeAndStateForPage(user
                , isTeacher, courseGroupId, name, state, page, size);
        return homeworkVos == null ? ResponseResult.enFail() : ResponseResult.enSuccess().add("list", homeworkVos)
                .add("size", homeworkVos.size());
    }
}
