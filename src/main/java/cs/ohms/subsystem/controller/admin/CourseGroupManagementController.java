package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.exception.NSRuntimeException;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.service.StudentService;
import cs.ohms.subsystem.utils.NStringUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 教学秘书
 *
 * @author LRC
 **/
@Controller
@RequestMapping("/teachingSecretary/courseGroupManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"}, logical = Logical.OR)
@Validated
@Slf4j
public class CourseGroupManagementController {
    private CourseGroupService courseGroupService;
    private StudentService studentService;
    private ResourceService resourceService;

    @Autowired
    public CourseGroupManagementController(CourseGroupService courseGroupService, StudentService studentService
            , ResourceService resourceService) {
        this.courseGroupService = courseGroupService;
        this.studentService = studentService;
        this.resourceService = resourceService;
    }

    /**
     * 显示课群管理
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index(@RequestParam(value = "courseGroup", required = false) @Min(1) Integer courseGroupId) {
        if (courseGroupId != null) {
            CourseGroupEntity courseGroup = courseGroupService.findById(courseGroupId);
            if (courseGroup == null) {
                throw new NSRuntimeException("正在查看不存在的课群详情！");
            }
            return (new ModelAndView("pages/admin/courseGroupManagementInfo").addObject("courseGroup", courseGroup));
        }
        return new ModelAndView("pages/admin/courseGroupManagement");
    }

    /**
     * 分布获取课程列表
     *
     * @param draw   获取次数
     * @param start  起点位置
     * @param length 长度
     * @return ResponseResult
     */
    @PostMapping("/courseGroupInfoList")
    @ResponseBody
    public ResponseResult courseGroupList(@RequestParam("draw") @NotNull @Min(1) Integer draw
            , @RequestParam("start") @NotNull @Min(0) Integer start
            , @RequestParam("length") @NotNull @Min(5) Integer length
            , @RequestParam("filterCourseGroupName") @Length(max = 64) String filterCourseGroupName
            , @RequestParam("filterTeacherId") @Length(max = 24) String filterTeacherId
            , @RequestParam("filterLogical") @NotNull Integer filterLogical) {
        int page = resourceService.calculatePageNum(start, length);
        if (NStringUtil.isEmpty(filterTeacherId)) {
            if (NStringUtil.isEmpty(filterCourseGroupName)) {
                return courseGroupService.getCourseGroupForPage(page, length).add("draw", draw);
            }
            return courseGroupService.getCourseGroupByNameForPage(filterCourseGroupName, page, length).add("draw", draw);
        }
        if (!NStringUtil.isEmpty(filterCourseGroupName)) {
            return (filterLogical == 0 ? courseGroupService.getCourseGroupByTeacherAndNameForPage(filterTeacherId, filterCourseGroupName, page, length)
                    : courseGroupService.getCourseGroupByTeacherOrNameForPage(filterTeacherId, filterCourseGroupName, page, length))
                    .add("draw", draw);
        }
        return courseGroupService.getCourseGroupByTeacherForPage(filterTeacherId, page, length).add("draw", draw);
    }

    /**
     * 添加课群
     *
     * @param courseGroupName 课群名
     * @param teacherId       负责该课群的教师
     * @param description     简介
     * @param studentIdsStr   加入课群的学生
     * @return ResponseResult
     */
    @PostMapping("/addCourseGroup")
    @ResponseBody
    public ResponseResult addCourseGroup(@RequestParam("courseGroupName") @NotEmpty @Length(min = 5, max = 64) String courseGroupName
            , @RequestParam("teacherId") @NotEmpty String teacherId
            , @RequestParam("description") String description
            , @RequestParam("studentIds") @NotNull @NotEmpty String studentIdsStr) {
        Set<String> studentIds = new HashSet<>(Arrays.asList(studentIdsStr.split("\n|(\r\n)|\r")));
        if (!studentService.testStudentId(studentIds)) {
            return ResponseResult.enFail();
        }
        return courseGroupService.addCourseGroup(courseGroupName, teacherId, description, studentIds)
                ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }

    /**
     * 由班级快速创建课群
     *
     * @param classId         班级id
     * @param courseGroupName 课群名
     * @param teacherId       教职工号
     * @param description     课群介绍
     * @return ResponseResult
     */
    @PostMapping("/addCourseGroupForClass")
    @ResponseBody
    public ResponseResult addCourseGroupForClass(@RequestParam("classId") @NotNull @Min(1) Integer classId
            , @RequestParam("courseGroupName") @NotEmpty @Length(min = 5, max = 64) String courseGroupName
            , @RequestParam("teacherId") @NotEmpty @Length(min = 5, max = 24) String teacherId
            , @RequestParam("description") String description) {
        return (courseGroupService.addCourseGroupForClass(classId, courseGroupName, teacherId, description)
                ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }

    /**
     * 更新课群信息
     *
     * @param courseGroupId   课群id
     * @param courseGroupName 课群名
     * @param teacherId       教职工号
     * @param description     课群介绍
     * @return ResponseResult
     */
    @PostMapping("/updateCourseGroup")
    @ResponseBody
    public ResponseResult updateCourseGroup(@RequestParam("courseGroupId") @NotNull @Min(1) Integer courseGroupId
            , @RequestParam("courseGroupName") @NotEmpty @Length(min = 5, max = 64) String courseGroupName
            , @RequestParam("teacherId") @NotEmpty String teacherId
            , @RequestParam("description") String description) {
        return (courseGroupService.updateCourseGroup(courseGroupId, courseGroupName, teacherId, description)
                ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }

    /**
     * 添加学生到指定的课群当中
     *
     * @param courseGroupId 课群id
     * @param studentIdsStr 学号列表
     * @return ResponseResult
     */
    @PostMapping("/addStudentToCourseGroup")
    @ResponseBody
    public ResponseResult addStudentToCourseGroup(@RequestParam("courseGroupId") @NotNull @Min(1) Integer courseGroupId
            , @RequestParam("studentIds") @NotNull @NotEmpty String studentIdsStr) {
        Set<String> studentIds = new HashSet<>(Arrays.asList(studentIdsStr.split("\n|(\r\n)|\r")));
        if (!studentService.testStudentId(studentIds)) {
            return ResponseResult.enFail();
        }
        return courseGroupService.addStudent2CourseGroup(courseGroupId, studentIds) ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }

    /**
     * 将学生从指定课群中移除
     *
     * @param courseGroupId 课群id
     * @param studentId     学号
     * @return ResponseResult
     */
    @PostMapping("/removeStudentForCourseGroup")
    @ResponseBody
    public ResponseResult removeStudentForCourseGroup(@RequestParam("courseGroupId") @NotNull @Min(1) Integer courseGroupId
            , @RequestParam("studentId") @NotNull @Length(min = 12, max = 12) String studentId) {
        if (studentService.testStudentId(studentId)) {
            return courseGroupService.removeStudentByStudentId(courseGroupId, studentId) ? ResponseResult.enSuccess() : ResponseResult.enFail();
        }
        return ResponseResult.enFail();
    }

    /**
     * 根据删除一条课群信息
     *
     * @param courseGroupId 课群id
     * @return ResponseResult
     */
    @PostMapping("/deleteOneCourseGroupInfo")
    @ResponseBody
    public ResponseResult deleteOneCourseGroupInfo(@RequestParam("courseGroupId") @NotNull @Min(1) Integer courseGroupId) {
        return courseGroupService.deleteCourseGroup(courseGroupId) ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }
}
