// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/15.
package cn.ohms.subsystem.freemarker;

import freemarker.template.TemplateMethodModelEx;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class ShiroIsAuth implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) {
        try {
            Subject subject = SecurityUtils.getSubject();
            return subject.isAuthenticated();
        }catch (UnavailableSecurityManagerException ignored){}
        return false;
    }
}
