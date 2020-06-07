// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/06.
package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.exception.NSRuntimeException;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.HomeworkService;
import cs.ohms.subsystem.service.UserService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/publishHomework")
@RequiresRoles(value = {"admin", "teachingSecretary", "teacher"}, logical = Logical.OR)
@Validated
public class PublishHomeworkController {
    private CourseGroupService courseGroupService;
    private HomeworkService homeworkService;

    @Autowired
    public PublishHomeworkController(CourseGroupService courseGroupService, HomeworkService homeworkService) {
        this.courseGroupService = courseGroupService;
        this.homeworkService = homeworkService;
    }

    /**
     * 发布作业view
     *
     * @param courseGroupId 课群id
     * @return view
     */
    @GetMapping
    public ModelAndView index(@RequestParam("courseGroup") @NotNull @Min(1) Integer courseGroupId) {
        CourseGroupEntity courseGroup = courseGroupService.findById(courseGroupId);
        if (courseGroup == null) {
            throw new NSRuntimeException("未找到课群相关信息");
        }
        return (new ModelAndView("pages/publishHomeworkView").addObject("courseGroup", courseGroup));
    }

    /**
     * 添加作业
     *
     * @param courseGroupId 课群id
     * @param title         作业标题
     * @param description   作业描述
     * @param filesStr      附件
     * @param startTimeStr  开始时间
     * @param endTimeStr    结束时间
     * @return ResponseResult
     */
    @PostMapping("/addHomework")
    @ResponseBody
    public ResponseResult addHomework(@RequestParam("courseGroupId") Integer courseGroupId
            , @RequestParam("title") @NotEmpty @Length(min = 1, max = 64) String title
            , @RequestParam("description") String description
            , @RequestParam("files") String filesStr
            , @RequestParam("startTime") @NotEmpty String startTimeStr
            , @RequestParam("endTime") @NotEmpty String endTimeStr) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, df);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, df);
        if (startTime.isAfter(endTime)) {/* 时间不正确 */
            return ResponseResult.enFail();
        }
        return (homeworkService.addHomework((UserEntity) SecurityUtils.getSubject().getSession().getAttribute(UserService.USER_SELF)
                , courseGroupId, title, description, filesStr, startTime, endTime) ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }
}
