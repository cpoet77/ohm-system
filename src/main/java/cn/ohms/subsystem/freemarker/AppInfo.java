// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/6.
package cn.ohms.subsystem.freemarker;

import cs.ohmsubsystem.service.AppService;
import freemarker.template.TemplateMethodModelEx;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通过属性名获取系统属性值
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Component("AppInfo")
public class AppInfo implements TemplateMethodModelEx {
    private AppService appService;

    @Autowired
    public AppInfo(AppService appService) {
        this.appService = appService;
    }

    @Override
    public Object exec(@NotNull List args) {
        if(args.isEmpty()){
            return "";
        }
        String name = String.valueOf(args.get(0));
        return appService.get(name);
    }
}
