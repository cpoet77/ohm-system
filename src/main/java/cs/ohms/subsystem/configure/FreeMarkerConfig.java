// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/6.
package cs.ohms.subsystem.configure;

import cs.ohms.subsystem.freemarker.AppInfo;
import cs.ohms.subsystem.freemarker.IsRoles;
import cs.ohms.subsystem.freemarker.ShiroGetUserInfo;
import cs.ohms.subsystem.freemarker.ShiroIsAuth;
import cs.ohms.subsystem.service.AppService;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * FreeMarkerConfig
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Configuration
public class FreeMarkerConfig {
    private freemarker.template.Configuration configuration;
    private AppService appService;
    private AppInfo appInfo;

    @Autowired
    public FreeMarkerConfig(freemarker.template.Configuration configuration, AppService appService
            , AppInfo appInfo) {
        this.configuration = configuration;
        this.appService = appService;
        this.appInfo = appInfo;
    }

    @PostConstruct
    public void proceed() throws TemplateModelException {
        configuration.setSharedVariable("OHMS_NAME", appService.getAsString("ohmsName"));
        configuration.setSharedVariable("OHMS_NAME_FORMAT", appService.getAsString("ohmsNameFormat"));
        configuration.setSharedVariable("OHMS_VERSION", appService.getAsString("version"));
        configuration.setSharedVariable("appInfo", appInfo);
        configuration.setSharedVariable("shiroGetUserInfo", new ShiroGetUserInfo());
        configuration.setSharedVariable("shiroIsAuth", new ShiroIsAuth());
        configuration.setSharedVariable("isRoles", new IsRoles());
    }
}
