package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.io.InputStream;

/**
 * 教学秘书
 *
 * @author LRC
 **/
@Controller
@RequestMapping("/teachingSecretary/courseGroupManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"})
@Slf4j
public class CourseGroupManagementController {
    private CourseGroupService courseGroupService;

    public CourseGroupManagementController(CourseGroupService courseGroupService) {
        this.courseGroupService = courseGroupService;
    }

    /**
     * 显示课群管理
     *
     * @return view
     */
    @GetMapping
    public String index(){
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
            ,@RequestParam("start") @NotNull @Min(0) Integer start
            ,@RequestParam("length") @NotNull @Min(5) Integer length){
        return courseGroupService.getCourseGroupByPage(start,length).add("draw",draw);
    }

    @PostMapping("/importCourseGroupInfo")
    @ResponseBody
    public ResponseResult importCourseGroupInfo(@RequestParam("courseGroupXls")@NotNull MultipartFile courseGroupXls){
        if (!courseGroupXls.isEmpty() && ".xlsx".equals(FileUtil.getFilePostfix(courseGroupXls.getOriginalFilename()))) {
            try (InputStream in = courseGroupXls.getInputStream()) {
                return courseGroupService.importCourseGroupInfo(in);
            } catch (IOException e) {
                log.warn("获取io流失败!", e);
            }
        }
        return ResponseResult.enFail();
    }

    /**
     * 保存课群信息
     *
     * @param id        课群id，id为null时是添加操作，否则为更新操作
     * @param courseGroupName 课程名
     * @param teacherRealName 教师真实姓名
     * @param description 课群描述
     * @param state 课群状态
     * @return ResponseResult
     */
    @PostMapping("/saveOneCourseGroupInfo")
    @ResponseBody
    public ResponseResult saveOneCourseGroupInfo(@RequestParam("id") @Min(1) Integer id
            , @RequestParam("teacherRealName") @NotNull String teacherRealName
            , @RequestParam("courseGroupName") @NotEmpty @Length(min = 2, max = 64) String courseGroupName
            , @RequestParam("description") String description
            , @RequestParam("state") String state){
        return courseGroupService.saveCourseGroup(id, teacherRealName, courseGroupName,  description, state) ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }

    /**
     * 根据删除一条课群信息
     *
     * @param id 课群id
     * @return ResponseResult
     */
    @PostMapping("/deleteOneCourseGroupInfo")
    @ResponseBody
    public ResponseResult deleteOneCourseGroupInfo(@RequestParam("id") @NotNull @Min(1) Integer id){
        return courseGroupService.deleteCourseGroup(id) ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }

}
