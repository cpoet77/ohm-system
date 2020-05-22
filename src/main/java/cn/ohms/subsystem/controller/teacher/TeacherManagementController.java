package cn.ohms.subsystem.controller.teacher;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.service.TeacherService;
import cn.ohms.subsystem.utils.FileUtil;
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
@Slf4j
@RequiresRoles(value = {"teachingSecretary"})
public class TeacherManagementController {
    private TeacherService teacherService;

    @Autowired
    public TeacherManagementController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * 教师管理
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("pages/teacherManagement");
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
        if (!teacherXls.isEmpty() && ".xlsx".equals(FileUtil.getFilePostfix(teacherXls.getOriginalFilename()))) {
            try (InputStream in = teacherXls.getInputStream()) {
                return teacherService.importTeacherInfo(in);
            } catch (IOException e) {
                log.error("获取IO流失败", e);
            }
        }
        return ResponseResult.enFail();
    }
}
