// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/24.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.entity.ClassEntity;
import cs.ohms.subsystem.entity.RoleEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.tableobject.StudentInfoTo;
import cs.ohms.subsystem.tableobject.TeacherInfoTo;
import org.springframework.transaction.annotation.Transactional;

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
     * 添加学生
     * <p><b>由于spring在同一server里面不能实现非事务方法触发事务方法回滚，因此将保存入库添加到userServer中</b></p>
     *
     * @param clazz         班级
     * @param studentRole   学生角色
     * @param studentInfoTo StudentInfoTo
     */
    @Transactional
    void saveUserIsStudent(ClassEntity clazz, RoleEntity studentRole, StudentInfoTo studentInfoTo);

    /**
     * 保存教师信息
     *
     * @param teacherRole   教师角色
     * @param teacherInfoTo 教师to对象
     */
    @Transactional
    void saveUserIsTeacher(RoleEntity teacherRole, TeacherInfoTo teacherInfoTo);

    /**
     * @param realName 真实姓名
     * @return UserEntity
     */
    UserEntity findUserByRealName(String realName);

    /**
     * 根据用户id删除用户
     *
     * @param currentUserIsAdmin 当前操作的用户是否是超级管理员
     * @param userId             用户id
     * @return true|false
     */
    boolean deleteUserById(Boolean currentUserIsAdmin, Integer userId);

    /**
     * 更新用户的基本信息
     *
     * @param currentUserIsAdmin 当前操作的用户是否是超级管理员
     * @param userId             用户id
     * @param realName           姓名
     * @param sex                用户性别
     * @param email              邮箱地址
     * @param phone              手机号
     * @return true|false
     */
    boolean updateUserById(Boolean currentUserIsAdmin, Integer userId, String realName, Character sex, String email, String phone);

    /**
     * 设置皮肤
     *
     * @param user     需要设置的用户
     * @param skinName 皮肤名称
     * @return true|false
     */
    boolean saveSkin(UserEntity user, String skinName);
}
