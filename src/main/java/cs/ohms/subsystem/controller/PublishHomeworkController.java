// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/06.
package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.exception.NSRuntimeException;
import cs.ohms.subsystem.service.CourseGroupService;
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
@RequestMapping("/publishHomework")
@RequiresRoles(value = {"admin", "teachingSecretary", "teacher"}, logical = Logical.OR)
@Validated
public class PublishHomeworkController {
    private CourseGroupService courseGroupService;

    @Autowired
    public PublishHomeworkController(CourseGroupService courseGroupService) {
        this.courseGroupService = courseGroupService;
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
}
