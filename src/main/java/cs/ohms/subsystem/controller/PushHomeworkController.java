// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.HomeworkEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.exception.NSRuntimeException;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.HomeworkService;
import cs.ohms.subsystem.service.PushHomeworkService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.viewobject.CourseGroupVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/pushHomework")
@RequiresRoles(value = {"student"})
@Validated
public class PushHomeworkController {
    private CourseGroupService courseGroupService;
    private HomeworkService homeworkService;
    private PushHomeworkService pushHomeworkService;

    @Autowired
    public PushHomeworkController(CourseGroupService courseGroupService, HomeworkService homeworkService
            , PushHomeworkService pushHomeworkService) {
        this.courseGroupService = courseGroupService;
        this.homeworkService = homeworkService;
        this.pushHomeworkService = pushHomeworkService;
    }

    /**
     * 提交作业页面
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index(@RequestParam("courseGroup") @NotNull @Min(1) Integer courseGroupId
            , @RequestParam("homework") @NotNull @Min(1) Integer homeworkId) {
        CourseGroupVo courseGroupVo = courseGroupService.findByStudentAndId((UserEntity) SecurityUtils.getSubject()
                .getSession().getAttribute(UserService.USER_SELF), courseGroupId);
        if (courseGroupVo == null) {
            throw new NSRuntimeException("非法访问课群？");
        }
        HomeworkEntity homework = homeworkService.findByCourseGroupAndId(courseGroupId, homeworkId);
        if (homework == null) {
            throw new NSRuntimeException("非法访问作业信息？");
        }
        return (new ModelAndView("pages/pushHomeworkView").addObject("courseGroup", courseGroupVo)
                .addObject("homework", homework));
    }

    /**
     * 提交作业
     *
     * @param homeworkId  作业id
     * @param description 描述
     * @param files       上传的文件
     * @return ResponseResult
     */
    @PostMapping("/pushHomework")
    @ResponseBody
    public ResponseResult pushHomework(@RequestParam("homeworkId") @NotNull @Min(1) Integer homeworkId
            , @RequestParam("description") String description
            , @RequestParam("files") String files) {
        return (pushHomeworkService.savePushHomework((UserEntity) SecurityUtils.getSubject().getSession()
                .getAttribute(UserService.USER_SELF), homeworkId, description, files) ? ResponseResult.enSuccess()
                : ResponseResult.enFail());
    }
}
