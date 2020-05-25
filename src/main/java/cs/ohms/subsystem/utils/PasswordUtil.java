// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/8.
package cs.ohms.subsystem.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 密码工具
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class PasswordUtil {
    /**
     * 生成随机盐
     * @return salt
     */
    public String produceSalt() {
        return (UUIDUtil.uuid32pure());
    }

    /**
     * 为密码加盐
     * @param password password
     * @param salt salt
     * @return encrypt password
     */
    public String encryptPassword(String password, String salt) {
        return (new Md5Hash(password, salt).toString());
    }
}
