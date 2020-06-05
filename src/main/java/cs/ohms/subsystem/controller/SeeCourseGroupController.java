package cs.ohms.subsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2020/6/4 11:42
 *
 * @author _Struggler
 */
@Controller
@RequestMapping("/seeCourseGroup")
public class SeeCourseGroupController {

    /**
     * 我的课群界面显示demo
     *
     * @return
     */
    @GetMapping
    public String seeCourseGroupIndex(){
        return "pages/seeCourseGroup";
    }

    /**
     * 创建作业界面显示demo
     *
     * @return
     */
    @GetMapping("/createHomework")
    public String createHomeworkIndex(){
        return "pages/createHomework";
    }

    /**
     * 提交作业视图显示demo
     *
     * @return
     */
    @GetMapping("/createHomework/pushHomework")
    public String pushHomeworkIndex(){
        return "pages/pushHomework";
    }
}
