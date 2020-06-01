package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

/**
 * 2020/5/22 16:47
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/teachingSecretary/teacherManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"})
@Slf4j
public class TeacherManagementController {
    private TeacherService teacherService;
    private ResourceService resourceService;

    @Autowired
    public TeacherManagementController(TeacherService teacherService, ResourceService resourceService) {
        this.teacherService = teacherService;
        this.resourceService = resourceService;
    }

    /**
     * 教师管理
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("pages/admin/teacherManagement");
        return view.addObject("teachers", teacherService.findAll());
    }

    /**
     * 导入教师信息
     *
     * @param teacherXls MultipartFile
     * @return ResponseResult
     */
    @PostMapping("/importTeacherInfo")
    @ResponseBody
    public ResponseResult importTeacherInfo(@RequestParam("teacherXls") @NotNull MultipartFile teacherXls) {
        if (resourceService.isDemandXlsFile(teacherXls)) {
            try (InputStream in = teacherXls.getInputStream()) {
                return teacherService.importTeacherInfo(in);
            } catch (IOException e) {
                log.error("获取IO流失败", e);
            }
        }
        return ResponseResult.enFail();
    }
}
