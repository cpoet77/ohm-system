// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/05.
package cs.ohms.subsystem.controller.admin;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.viewobject.LogFileInfoVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Controller
@RequestMapping("/admin/systemRunLog")
@RequiresRoles(value = {"admin"})
@Validated
public class SystemRunLogController {
    private ResourceService resourceService;

    @Autowired
    public SystemRunLogController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 查看系统运行日志
     *
     * @return view
     */
    @GetMapping
    public String index() {
        return "pages/admin/systemRunLogView";
    }

    /**
     * 获取所有日志文件列表
     *
     * @return ResponseResult
     */
    @PostMapping("/allLogFileList")
    @ResponseBody
    public ResponseResult allLogFileList() {
        List<LogFileInfoVo> logFileInfoVoList = resourceService.getAllLogFileList();
        return logFileInfoVoList == null ? ResponseResult.enFail() : ResponseResult.enSuccess()
                .add("list", logFileInfoVoList).add("length", logFileInfoVoList.size());
    }

    /**
     * 获取日志文件内容
     *
     * @param fileName 文件名
     * @return ResponseResult
     */
    @PostMapping("/getLogFileContent")
    @ResponseBody
    public ResponseResult getLogFileContent(@RequestParam("fileName") @NotEmpty @Pattern(regexp = "^log[0-9\\-]*\\.txt$") String fileName) {
        String content = resourceService.getLogFileContent(fileName);
        return (content == null) ? ResponseResult.enFail() : ResponseResult.enSuccess().add("content", content);
    }
}
