// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.HomeworkEntity;
import cs.ohms.subsystem.entity.PushHomeworkEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.exception.NSRuntimeException;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.PushHomeworkService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.viewobject.CourseGroupVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 教师查看作业详情并完成评分
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/pushHomeworkInfo")
@RequiresRoles(value = {"admin", "teachingSecretary", "teacher"}, logical = Logical.OR)
@Validated
public class PushHomeworkInfoController {
    private CourseGroupService courseGroupService;
    private PushHomeworkService pushHomeworkService;

    @Autowired
    public PushHomeworkInfoController(CourseGroupService courseGroupService, PushHomeworkService pushHomeworkService) {
        this.courseGroupService = courseGroupService;
        this.pushHomeworkService = pushHomeworkService;
    }

    /**
     * 显示提交的作业详情
     *
     * @param courseGroupId  课群id
     * @param homeworkId     作业id
     * @param pushHomeworkId 提交作业id
     * @return view
     */
    @GetMapping
    public ModelAndView index(@RequestParam("courseGroup") @NotNull @Min(1) Integer courseGroupId
            , @RequestParam("homework") @NotNull @Min(1) Integer homeworkId
            , @RequestParam("pushHomework") @NotNull @Min(1) Integer pushHomeworkId) {
        CourseGroupVo courseGroup = courseGroupService.findByTeacherAndId((UserEntity) SecurityUtils.getSubject().getSession().getAttribute(UserService.USER_SELF)
                , courseGroupId);
        if (courseGroup == null) {
            throw new NSRuntimeException();
        }
        PushHomeworkEntity pushHomework = pushHomeworkService.findById(pushHomeworkId);
        HomeworkEntity homework = pushHomework.getHomework();
        if (!courseGroupId.equals(courseGroup.getId()) || !courseGroup.getId().equals(homework.getCourseGroup().getId())) {
            throw new NSRuntimeException();
        }
        return (new ModelAndView("pages/pushHomeworkInfoView").addObject("courseGroup", courseGroup)
                .addObject("homework", homework).addObject("pushHomework", pushHomework));
    }

    /**
     * 保存教师对学生提交作业的评价
     *
     * @param pushHomeworkId 提交作业id
     * @param score          分数
     * @param assess         评价
     * @return ResponseResult
     */
    @PostMapping("/saveScore")
    @ResponseBody
    public ResponseResult saveScore(@RequestParam("pushHomeworkId") @NotNull @Min(1) Integer pushHomeworkId
            , @RequestParam("score") @NotNull @Min(0) @Max(100) Integer score
            , @RequestParam("assess") String assess) {
        return pushHomeworkService.saveScore((UserEntity) SecurityUtils.getSubject().getSession().getAttribute(UserService.USER_SELF)
                , pushHomeworkId, score, assess) ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }
}
