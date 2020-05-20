// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/24.
package cn.ohms.subsystem.service;

import cs.ohmsubsystem.dto.UserDto;
import cs.ohmsubsystem.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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
     * 存储语言Cookie所有名
     */
    String LANGUAGE_SELF_COOKIE = "locale_language";

    /**
     * 判断用户名是否已存在
     *
     * @return true|false
     */
    boolean containUserName(String userName);

    /**
     * 生成帐号激活url
     *
     * @param userId 用户id
     * @param email  邮箱地址
     * @return url
     */
    String generateActivationUrl(int userId, String email);

    /**
     * 用户注册
     * <p><b>注册过程中，如果存在错误，事务将回滚</b></p>
     *
     * @param userDto   UserDto
     * @param roleName  默认角色名
     * @param object    studentEntity|teacherEntity
     * @param isTeacher ?teacher
     * @param password  password
     * @return true|false
     */
    @Transactional
    boolean register(UserDto userDto, String roleName, Object object, String password, boolean isTeacher);

    /**
     * 帐号激活
     *
     * @param userId userId
     * @param email  email
     * @return true|false
     */
    boolean accountActivation(int userId, String email);

    /**
     * Get user entity
     *
     * @param email email地址
     * @return UserEntity
     */
    UserEntity getUserByEmail(String email);

    /**
     * 获取用户的完整信息
     * <p>在baseInfo的基础上添加信息</p>
     *
     * @param userId   用户id
     * @param userName 用户名
     * @return map info
     */
    Map<String, Object> findUserProfileInfo(int userId, String userName);

    /**
     * 获取用户的基础信息
     *
     * @param userId   用户id
     * @param userName 用户名
     * @return info map
     */
    Map<String, Object> findUserProfileBaseInfo(int userId, String userName);
}
