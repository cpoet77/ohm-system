package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 教学秘书
 *
 * @author LRC
 **/
@Controller
@RequestMapping("/teachingSecretary/majorManagement")
@RequiresRoles(value = {"admin", "teachingSecretary"})
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
    public String index() {
        return "pages/admin/majorManagement";
    }

    /**
     * 分布获取专业列表
     *
     * @param draw            获取次数
     * @param start           起点位置
     * @param length          长度
     * @param filterCollegeId 过滤的学院id
     * @return ResponseResult
     */
    @PostMapping("/majorInfoList")
    @ResponseBody
    public ResponseResult majorInfoList(@RequestParam("draw") @NotNull @Min(1) Integer draw
            , @RequestParam("start") @NotNull @Min(0) Integer start
            , @RequestParam("length") @NotNull @Min(5) Integer length
            , @RequestParam("filterCollegeId") Integer filterCollegeId) {
        return majorService.getMajorByCollegeAndPage(filterCollegeId, start, length).add("draw", draw);
    }

    /**
     * 获取专业信息列表
     *
     * @param collegeId 学院id
     * @return ResponseResult
     */
    @PostMapping("/majorInfoListByCollege")
    @ResponseBody
    public ResponseResult majorInfoListByCollege(@RequestParam("collegeId") Integer collegeId) {
        return ResponseResult.enSuccess().add("majors", majorService.findAllVoByCollege(collegeId));
    }

    /**
     * 保存专业信息
     *
     * @param id        专业id，id为null时是添加操作，否则为更新操作
     * @param majorName 专业名
     * @param collegeId 学院id
     * @return ResponseResult
     */
    @PostMapping("/saveOneMajorInfo")
    @ResponseBody
    public ResponseResult saveOneMajorInfo(@RequestParam("id") @Min(1) Integer id
            , @RequestParam("majorName") @NotEmpty @Length(min = 4, max = 64) String majorName
            , @RequestParam("collegeId") @NotNull @Min(1) Integer collegeId) {
        return (majorService.saveMajor(id, majorName, collegeId) ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }

    /**
     * 根据删除一条专业信息
     *
     * @param majorId 专业id
     * @return ResponseResult
     */
    @PostMapping("/deleteOneMajorInfo")
    @ResponseBody
    public ResponseResult deleteOneMajorInfo(@RequestParam("majorId") @NotNull @Min(1) Integer majorId) {
        return (majorService.deleteMajor(majorId) ? ResponseResult.enSuccess() : ResponseResult.enFail());
    }
}
