// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/24.
package cn.ohms.subsystem.service;

import cn.ohms.subsystem.entity.UserEntity;

/**
 * UserService
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface UserService {
    /**
     * 存储session所用名
     */
    String USER_SELF = "ohms_user_self";

    /**
     * @param username 用户名
     * @return UserEntity
     */
    UserEntity findUserByName(String username);
}
