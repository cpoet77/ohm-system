// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/15.
package cs.ohms.subsystem.freemarker;

import freemarker.template.TemplateMethodModelEx;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 判断用户是否含有某种角色
 * <p><b>可判断多个，例如：isRoles(admin,teacher...)</b></p>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class IsRoles implements TemplateMethodModelEx {
    @Override
    public Object exec(@NotNull List list) {
        if (list.isEmpty()) {
            return false;
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            for (Object o : list) {
                if (subject.hasRole(String.valueOf(o))) {
                    return true;
                }
            }
        } catch (UnavailableSecurityManagerException ignored) {
        }
        return false;
    }
}
