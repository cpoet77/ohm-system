package cs.ohms.subsystem.controller.teacher;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.CollegeService;
import cs.ohms.subsystem.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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
 */
@Controller
@RequestMapping("/teachingSecretary/collegeManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"})
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
    public String index() {
        return "pages/collegeManagement";
    }

    /**
     * 获取学院信息列表
     *
     * @return ResponseResult
     */
    @PostMapping("/collegeInfoList")
    @ResponseBody
    public ResponseResult collegeInfoList(@RequestParam("draw") @NotNull @Min(1) Integer draw
            , @RequestParam("start") @NotNull @Min(0) Integer start
            , @RequestParam("length") @NotNull @Min(5) Integer length) {
        return (collegeService.getCollegesByPage(start, length).add("draw", draw));
    }

    /**
     * 获取所有学院基本信息列表
     *
     * @return ResponseResult
     */
    @PostMapping("/collegeInfoAllList")
    @ResponseBody
    public ResponseResult collegeInfoAllList() {
        return ResponseResult.enSuccess().add("colleges", collegeService.findAll());
    }

    /**
     * 导入学院信息
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

    /**
     * 保存单条学院信息
     *
     * @param id                 学院id,null进为添加学院
     * @param collegeName        学院名
     * @param collegeDescription 学院简介
     * @return ResponseResult
     */
    @PostMapping("/saveOneCollegeInfo")
    @ResponseBody
    public ResponseResult saveOneCollegeInfo(@RequestParam(value = "id") @Min(1) Integer id
            , @RequestParam("name") @NotEmpty @Length(min = 2, max = 64) String collegeName
            , @RequestParam("description") String collegeDescription) {
        return (collegeService.saveCollege(id, collegeName, collegeDescription) ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }

    /**
     * 删除单条学院信息
     *
     * @param id 学院id
     * @return ResponseResult
     */
    @PostMapping("/deleteCollegeInfo")
    @ResponseBody
    public ResponseResult deleteCollegeInfo(@RequestParam("collegeId") @NotNull @Min(1) Integer id) {
        return (collegeService.deleteCollege(id) ? ResponseResult.enSuccess().add("id", id) : ResponseResult.enFail()
                .add("id", id));
    }
}
