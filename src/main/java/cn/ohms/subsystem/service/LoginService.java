// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/8.
package cn.ohms.subsystem.service;

import cn.ohms.subsystem.entity.UserEntity;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface LoginService {
    /**
     * 登录记录
     * <p><b>开启异步，不考虑记录结果</b></p>
     *
     * @param request HttpServletRequest
     * @param user UserEntity
     */
    @Async
    void loginRecord(HttpServletRequest request, UserEntity user);
}
