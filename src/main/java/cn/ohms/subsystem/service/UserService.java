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
     * 超级管理员
     */
    String USER_ADMIN_ROLE = "admin";

    /**
     * 教学秘书
     */
    String USER_TEACHING_SECRETARY_ROLE = "teachingSecretary";

    /**
     * 教师
     */
    String USER_TEACHER_ROLE = "teacher";

    /**
     * 学生
     */
    String USER_STUDENT_ROLE = "student";

    /**
     * 存储session所用名
     */
    String USER_SELF = "ohms_user_self";

    /**
     * @param username 用户名
     * @return UserEntity
     */
    UserEntity findUserByName(String username);

    /**
     * 生成带前缀的用户名
     *
     * @param name 传入能唯一识别的名称
     * @return 最终用户名
     */
    String createDefaultName(String name);

    /**
     * 保存用户
     *
     * @param user UserEntity
     * @return true|false
     */
    boolean saveUser(UserEntity user);

    /**
     *
     *
     * @param realName 真实姓名
     * @return UserEntity
     */
    UserEntity findUserByRealName(String realName);


}
