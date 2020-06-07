package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.exception.NSRuntimeException;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.viewobject.CourseGroupListVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;

/**
 * 2020/6/4 11:42
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/myCourseGroup")
@RequiresAuthentication
@Validated
public class MyCourseGroupController {
    private CourseGroupService courseGroupService;

    @Autowired
    public MyCourseGroupController(CourseGroupService courseGroupService) {
        this.courseGroupService = courseGroupService;
    }

    /**
     * 我的课群
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index(@RequestParam(value = "page", required = false, defaultValue = "0") @Min(0) Integer page
            , @RequestParam(value = "search", required = false) String search) {
        final int length = 8;/* 每页最多显示8条 */
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = (UserEntity) subject.getSession().getAttribute(UserService.USER_SELF);
        if (subject.hasRole(UserService.USER_ADMIN_ROLE) || subject.hasRole(UserService.USER_TEACHING_SECRETARY_ROLE)
                || subject.hasRole(UserService.USER_TEACHER_ROLE)) {/* 教师用户 */
            CourseGroupListVo courseGroupListVo = NStringUtil.isEmpty(search) ? courseGroupService
                    .getCourseGroupListByTeacherForPage(user, page, length) : courseGroupService
                    .getCourseGroupListByTeacherAndNameForPage(user, search, page, length);
            if (courseGroupListVo == null || courseGroupListVo.getPage() != 0 && page >= courseGroupListVo.getPage()) {
                throw new NSRuntimeException("获取教师的课群信息失败？");
            }
            return (new ModelAndView("pages/myCourseGroupByTeacher").addObject("search", search)
                    .addObject("courseGroupListVo", courseGroupListVo).addObject("page", page));
        }
        CourseGroupListVo courseGroupListVo = NStringUtil.isEmpty(search) ? courseGroupService
                .getCourseGroupListByStudentForPage(user, page, length) : courseGroupService
                .getCourseGroupListByStudentAndNameForPage(user, search, page, length);
        if (courseGroupListVo == null || courseGroupListVo.getPage() != 0 && page >= courseGroupListVo.getPage()) {
            throw new NSRuntimeException("获取学生加入的课群信息失败？");
        }
        return (new ModelAndView("pages/myCourseGroupByStudent").addObject("search", search)
                .addObject("courseGroupListVo", courseGroupListVo).addObject("page", page));
    }
}
