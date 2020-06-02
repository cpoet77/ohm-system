package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequiresRoles(value = {"admin", "teachingSecretary"})
@Validated
@Slf4j
public class CourseGroupManagementController {
    private CourseGroupService courseGroupService;
    private StudentService studentService;

    public CourseGroupManagementController(CourseGroupService courseGroupService, StudentService studentService) {
        this.courseGroupService = courseGroupService;
        this.studentService = studentService;
    }

    /**
     * 显示课群管理
     *
     * @return view
     */
    @GetMapping
    public String index() {
        return "pages/admin/courseGroupManagement";
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
    public ResponseResult courseGoupList(@RequestParam("draw") @NotNull @Min(1) Integer draw
            , @RequestParam("start") @NotNull @Min(0) Integer start
            , @RequestParam("length") @NotNull @Min(5) Integer length) {
        return courseGroupService.getCourseGroupByPage(start, length).add("draw", draw);
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
        Set<String> studentIds = new HashSet<>(Arrays.asList(studentIdsStr.split("\n")));
        if (studentService.testStudentId(studentIds)) {
            return ResponseResult.enFail();
        }
        return courseGroupService.addCourseGroup(courseGroupName, teacherId, description, studentIds)
                ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }

    /**
     * 根据删除一条课群信息
     *
     * @param id 课群id
     * @return ResponseResult
     */
    @PostMapping("/deleteOneCourseGroupInfo")
    @ResponseBody
    public ResponseResult deleteOneCourseGroupInfo(@RequestParam("id") @NotNull @Min(1) Integer id) {
        return courseGroupService.deleteCourseGroup(id) ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }

}
