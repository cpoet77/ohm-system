package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.CollegeService;
import lombok.extern.slf4j.Slf4j;
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
 * 教学秘书
 *
 * @author LRC
 */
@Controller
@RequestMapping("/teachingSecretary/collegeManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"}, logical = Logical.OR)
@Validated
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
        return "pages/admin/collegeManagement";
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
