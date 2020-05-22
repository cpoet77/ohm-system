package cn.ohms.subsystem.controller.teacher;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.service.CollegeService;
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
 * 教学秘书
 *
 * @author LRC
 */
@Controller
@RequestMapping("/teachingSecretary/collegeManagement")
@RequiresRoles(value = {"teachingSecretary"})
@Slf4j
public class CollegeManagementController {
    private CollegeService collegeService;

    @Autowired
    public CollegeManagementController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    /**
     * 显示学院管理
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("pages/collegeManagement");
        return view.addObject("colleges", collegeService.findAll());
    }

    /**
     * 获取学院信息
     *
     * @param collegeXls excel表格文件
     * @return ResponseResult
     */
    @PostMapping("/importCollegeInfo")
    @ResponseBody
    public ResponseResult importCollegeInfo(@RequestParam("collegeXls") @NotNull MultipartFile collegeXls) {
        if (!collegeXls.isEmpty() && ".xlsx".equals(FileUtil.getFilePostfix(collegeXls.getOriginalFilename()))) {
            try (InputStream in = collegeXls.getInputStream()) {
                return collegeService.importCollegeInfo(in);
            } catch (IOException e) {
                log.warn("获取io流失败!", e);
            }
        }
        return ResponseResult.enFail();
    }
}
