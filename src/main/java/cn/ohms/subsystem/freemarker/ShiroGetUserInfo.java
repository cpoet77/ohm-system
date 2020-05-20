// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/15.
package cn.ohms.subsystem.freemarker;

import cs.ohmsubsystem.service.UserService;
import freemarker.template.TemplateMethodModelEx;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 协助view获取user信息
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class ShiroGetUserInfo implements TemplateMethodModelEx {
    @Override
    public Object exec(@NotNull List args) {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                Object object = subject.getSession().getAttribute(UserService.USER_SELF);
                if (null != object) {
                    return object;
                }
            }
        } catch (UnavailableSecurityManagerException ignored) {}
        return (new Object());
    }
}
