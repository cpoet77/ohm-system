package cn.ohms.subsystem.controller.teacher;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.service.MajorService;
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
 **/
@Controller
@RequestMapping("/teachingSecretary/majorManagement")
@RequiresRoles(value = {"teachingSecretary"})
@Slf4j
public class MajorManagementController {
    private MajorService majorService;

    @Autowired
    public MajorManagementController(MajorService majorService) {
        this.majorService = majorService;
    }

    /**
     * 显示专业管理
     *
     * @return view
     */
    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("/pages/majorManagement");
        return view.addObject("majors",majorService.findAll());
    }

    /**
     * 获取专业信息
     *
     * @param majorXls excel表格文件
     * @return ResponseResult
     */
    @PostMapping("/importMajorInfo")
    @ResponseBody
    public ResponseResult importMajorInfo(@RequestParam("majorXls")@NotNull MultipartFile majorXls){
        if (!majorXls.isEmpty() && ".xlsx".equals(FileUtil.getFilePostfix(majorXls.getOriginalFilename()))) {
            try (InputStream in = majorXls.getInputStream()) {
                return majorService.importMajorInfo(in);
            } catch (IOException e) {
                log.warn("获取io流失败!", e);
            }
        }
        return ResponseResult.enFail();

    }

}
