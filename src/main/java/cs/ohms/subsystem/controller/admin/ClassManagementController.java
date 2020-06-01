// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/30.
package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/teachingSecretary/classManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"})
@Validated
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
            , @RequestParam("collegeId") @NotNull @Min(-1) Integer collegeId
            , @RequestParam("majorId") @NotNull @Min(-1) Integer majorId) {
        return (classService.getClassByCollegeAndMajorAndPage(collegeId ,majorId, start, length).add("draw", draw));
    }

    /**
     * 保存班级信息，当classId为null时为新增记录，否则为更新
     *
     * @param classId   班级id
     * @param className 班级名
     * @param majorId   所学专业id
     * @return ResponseResult
     */
    @PostMapping("/saveOneClassInfo")
    @ResponseBody
    public ResponseResult saveOneClassInfo(@RequestParam("classId") Integer classId
            , @RequestParam("className") @NotNull @Length(min = 1, max = 10) String className
            , @RequestParam("majorId") @NotNull @Min(1) Integer majorId) {
        return classService.saveClass(classId, className, majorId) ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }

    /**
     * 删除班级
     *
     * @param classId 班级id
     * @return ResponseResult
     */
    @PostMapping("/deleteClass")
    @ResponseBody
    public ResponseResult deleteClass(@RequestParam("classId") @NotNull @Min(1) Integer classId) {
        return classService.deleteClass(classId) ? ResponseResult.enSuccess() : ResponseResult.enFail();
    }
}
