package cs.ohms.subsystem.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 2020/6/4 17:06
 *
 * @author LRC
 **/
@Controller
@RequestMapping("/homework")
@RequiresAuthentication
@Validated
public class HomeworkController {
    /**
     * 显示课群下的作业信息
     * <p>包括教师发布作业</p>
     *
     * @return view
     */
    @GetMapping
    public String index(@RequestParam("courseGroup") @NotNull @Min(1) Integer courseGroupId) {
        return "pages/homeworkView";
    }
}
