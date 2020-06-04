package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.service.StudentService;
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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 2020/5/22 14:08
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/teachingSecretary/studentManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"}, logical = Logical.OR)
@Validated
@Slf4j
public class StudentManagementController {
    private StudentService studentService;
    private UserService userService;
    private ResourceService resourceService;

    @Autowired
    public StudentManagementController(StudentService studentService, UserService userService, ResourceService resourceService) {
        this.studentService = studentService;
        this.userService = userService;
        this.resourceService = resourceService;
    }

    /**
     * 学生管理
     *
     * @return view
     */
    @GetMapping
    public String index() {
        return "pages/admin/studentManagement";
    }

    /**
     * 附带过滤条件的分页获取学生列表
     *
     * @param draw      请求次数
     * @param start     起点位置
     * @param length    长度
     * @param collegeId 过滤学院id
     * @param majorId   过滤专业id
     * @param classId   过滤班级id
     * @return ResponseResult
     */
    @PostMapping("/studentList")
    @ResponseBody
    public ResponseResult studentList(@RequestParam("draw") @NotNull @Min(1) Integer draw
            , @RequestParam("start") @NotNull @Min(0) Integer start
            , @RequestParam("length") @NotNull @Min(5) Integer length
            , @RequestParam("collegeId") @NotNull @Min(-1) Integer collegeId
            , @RequestParam("majorId") @NotNull @Min(-1) Integer majorId
            , @RequestParam("classId") @NotNull @Min(-1) Integer classId) {
        int page = resourceService.calculatePageNum(start, length);
        if (classId > 0) {
            return studentService.getStudentListByClassAndPage(classId, page, length).add("draw", draw);
        } else if (majorId > 0) {
            return studentService.getStudentListByMajorAndPage(majorId, page, length).add("draw", draw);
        } else if (collegeId > 0) {
            return studentService.getStudentListByCollegeAndPage(collegeId, page, length).add("draw", draw);
        }
        return studentService.getStudentListByPage(page, length).add("draw", draw);
    }

    /**
     * 获取指定课群下的学生
     * <p>支持通过学号和学生的姓名进行过滤</p>
     *
     * @param draw              获取次数
     * @param start             起点位置
     * @param length            数量
     * @param courseGroupId     课群id
     * @param filterStudentId   过滤学号
     * @param filterStudentName 过滤学生姓名
     * @param filterLogical     过滤关系，学号和姓名同时设置时有效
     * @return ResponseResult
     */
    @PostMapping("/courseGroupStudentList")
    @ResponseBody
    public ResponseResult courseGroupStudentList(@RequestParam("draw") @NotNull @Min(1) Integer draw
            , @RequestParam("start") @NotNull @Min(0) Integer start
            , @RequestParam("length") @NotNull @Min(5) Integer length
            , @RequestParam("courseGroupId") @NotNull @Min(1) Integer courseGroupId
            , @RequestParam("filterStudentId") @Length(max = 12) String filterStudentId
            , @RequestParam("filterStudentName") @Length(max = 5) String filterStudentName
            , @RequestParam("filterLogical") @NotNull Integer filterLogical) {
        int page = resourceService.calculatePageNum(start, length);
        if (NStringUtil.isEmpty(filterStudentId)) {
            if (NStringUtil.isEmpty(filterStudentName)) {
                return studentService.getCourseGroupStudentList(courseGroupId, page, length).add("draw", draw);
            }
            return studentService.getCourseGroupStudentListByName(courseGroupId, filterStudentName, page, length).add("draw", draw);
        }
        if (!NStringUtil.isEmpty(filterStudentName)) {
            return studentService.getCourseGroupStudentList(courseGroupId, filterStudentId, filterStudentName
                    , filterLogical == 0, page, length).add("draw", draw);
        }
        return studentService.getCourseGroupStudentListByStudentId(courseGroupId, filterStudentId, page, length).add("draw", draw);
    }

    /**
     * 保存学生信息
     * <p><b>当userId不为空时为更新数据，否则为新增用户</b></p>
     *
     * @param userId    用户id
     * @param studentId 学号
     * @param realName  姓名
     * @param sex       性别
     * @param email     邮箱地址
     * @param phone     手机号
     * @param classId   班级id
     * @return ResponseResult
     */
    @PostMapping("/saveStudent")
    @ResponseBody
    public ResponseResult saveStudent(@RequestParam("userId") @Min(1) Integer userId
            , @RequestParam("studentId") @NotEmpty @Length(min = 12, max = 12) String studentId
            , @RequestParam("realName") @NotEmpty @Length(min = 2, max = 5) String realName
            , @RequestParam("sex") @NotNull @NSCharCheck(value = {'M', 'F'}, message = "请确定学生性别") Character sex
            , @RequestParam("email") @NSEmail(message = "邮箱格式不正确") String email
            , @RequestParam("phone") @NSPhone(message = "手机号格式不正确") String phone
            , @RequestParam("classId") @NotNull @Min(1) Integer classId) {
        if (userId != null) {
            return (studentService.updateStudent(userId, realName, sex, email, phone, classId)
                    ? ResponseResult.enSuccess() : ResponseResult.enFail());
        }
        return (studentService.insertStudent(studentId, realName, sex, email, phone, classId)
                ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }

    /**
     * 删除学生信息
     *
     * @param userId 用户id
     * @return ResponseResult
     */
    @PostMapping("/deleteStudent")
    @ResponseBody
    public ResponseResult deleteStudent(@RequestParam("userId") @NotNull @Min(1) Integer userId) {
        return (userService.deleteUserById(SecurityUtils.getSubject().hasRole(UserService.USER_ADMIN_ROLE), userId)
                ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }
}
