// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.entity.HomeworkEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.exception.NSRuntimeException;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.HomeworkService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.vo.CourseGroupVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/pushHomeworkList")
@RequiresRoles(value = {"admin", "teachingSecretary", "teacher"}, logical = Logical.OR)
@Validated
public class PushHomeworkListController {
    private CourseGroupService courseGroupService;
    private HomeworkService homeworkService;

    @Autowired
    public PushHomeworkListController(CourseGroupService courseGroupService, HomeworkService homeworkService) {
        this.courseGroupService = courseGroupService;
        this.homeworkService = homeworkService;
    }

    /**
     * 教师查看学生以提交的作业列表
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index(@RequestParam("courseGroup") @NotNull @Min(1) Integer courseGroupId
            , @RequestParam("homework") @NotNull @Min(1) Integer homeworkId) {
        CourseGroupVo courseGroup = courseGroupService.findByTeacherAndId((UserEntity) SecurityUtils.getSubject().getSession().getAttribute(UserService.USER_SELF)
                , courseGroupId);
        if (courseGroup == null) {
            throw new NSRuntimeException();
        }
        HomeworkEntity homework = homeworkService.findByCourseGroupAndId(courseGroupId, homeworkId);
        if (homework == null) {
            throw new NSRuntimeException();
        }
        return (new ModelAndView("pages/pushHomeworkListView").addObject("courseGroup", courseGroup)
                .addObject("homework", homework).addObject("pushHomeworkSize", homework.getPushHomework().size()));
    }
}
