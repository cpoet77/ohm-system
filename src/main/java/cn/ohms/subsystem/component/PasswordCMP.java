// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/13.
package cn.ohms.subsystem.component;

import cn.ohms.subsystem.utils.PasswordUtil;
import org.springframework.stereotype.Component;

/**
 * password component
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Component
public class PasswordCMP {
    /**
     * 生成用于密码加密的盐
     *
     * @return salt 32
     */
    public String produceSalt(){
        return (new PasswordUtil().produceSalt());
    }

    /**
     * 加密密码
     *
     * @param password 密码
     * @param salt     用于加密的盐
     * @return 加密后的hash 32
     */
    public String encryptPassword(String password, String salt){
        return (new PasswordUtil().encryptPassword(password, salt));
    }
}
