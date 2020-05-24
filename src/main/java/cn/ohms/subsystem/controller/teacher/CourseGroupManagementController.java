package cn.ohms.subsystem.controller.teacher;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.service.CourseGroupService;
import cn.ohms.subsystem.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

/**
 * 教学秘书
 *
 * @author LRC
 **/
@Controller
@RequestMapping("/teachingSecretary/courseGroupManagement")
@RequiresRoles(value = {"teachingSecretary"})
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
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("/pages/courseGroupManagement");
        return view.addObject("courseGroups",courseGroupService.findAll());
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
}
