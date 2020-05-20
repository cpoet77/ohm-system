// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/8.
package cn.ohms.subsystem.common;

import cs.ohmsubsystem.utils.PasswordUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义password加密验证
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        SimpleAuthenticationInfo saInfo = (SimpleAuthenticationInfo) info;
        String dbPassword = String.valueOf((char[]) saInfo.getCredentials());
        String dbSalt = saInfo.getCredentialsSalt().toString();
        return dbPassword.equals(new PasswordUtil().encryptPassword(String.valueOf(upToken.getPassword()), dbSalt));
    }
}
