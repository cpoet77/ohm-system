// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/30.
package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.ClassService;
import cs.ohms.subsystem.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/teachingSecretary/classManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"})
@Slf4j
public class ClassManagementController {
    private ClassService classService;

    @Autowired
    public ClassManagementController(ClassService classService) {
        this.classService = classService;
    }

    /**
     * 显示班级管理
     *
     * @return view
     */
    @GetMapping
    public String index() {
        return "pages/admin/classManagement";
    }

    /**
     * 分页获取班级基本信息
     *
     * @param draw    获取的次数
     * @param start   开始位置
     * @param length  长度
     * @param majorId 根据专业过滤
     * @return ResponseResult
     */
    @PostMapping("/classInfoList")
    @ResponseBody
    public ResponseResult classInfoList(@RequestParam("draw") @NotNull @Min(1) Integer draw
            , @RequestParam("start") @NotNull @Min(0) Integer start
            , @RequestParam("length") @NotNull @Min(5) Integer length
            , @RequestParam("majorId") @Min(1) Integer majorId) {
        return (classService.getClassByMajorAndPage(majorId, start, length).add("draw", draw));
    }

    @PostMapping("/importClassInfo")
    @ResponseBody
    public ResponseResult importClassInfo(@RequestParam("classXls") @NotNull MultipartFile classXls) {
        if (!classXls.isEmpty() && ".xlsx".equals(FileUtil.getFilePostfix(classXls.getOriginalFilename()))) {
            try (InputStream in = classXls.getInputStream()) {
                return classService.importMajorInfo(in);
            } catch (IOException e) {
                log.warn("获取io流失败!", e);
            }
        }
        return ResponseResult.enFail();
    }
}
