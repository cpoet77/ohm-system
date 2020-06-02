// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.TeacherEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface TeacherService {
    /**
     * 返回所有教师信息
     *
     * @return TeacherEntity for list
     */
    List<TeacherEntity> findAll();

    /**
     * 分页获取用户信息，findTeacherName作为过滤条件
     *
     * @param start           分页起点
     * @param length          长度
     * @param findTeacherName 过滤条件
     * @return ResponseResult
     */
    ResponseResult findTeacherByTeacherNameAndPage(Integer start, Integer length, String findTeacherName);

    /**
     * 分页获取教师信息
     *
     * @param start  分页起点
     * @param length 长度
     * @return ResponseResult
     */
    ResponseResult findTeacherByPage(Integer start, Integer length);

    /**
     * 保存教师信息
     *
     * @param currentUserIsAdmin 是否是超级管理员
     * @param userId             用户id,为null时为新增
     * @param teacherId          教职工号
     * @param realName           用户名
     * @param sex                教师性别
     * @param phone              手机号
     * @param email              邮箱地址
     * @return true|false
     */
    @Transactional
    boolean saveTeacher(Boolean currentUserIsAdmin, Integer userId, String teacherId, String realName, Character sex, String phone, String email);

    /**
     * 添加/删除教学秘书角色
     *
     * @param userId 用户id
     * @return true|false
     */
    boolean changeTeachingSecretaryRole(Integer userId);

    /**
     * 根据name查询teacher
     * <p>在缓存有效期内，查找cache - database</p>
     *
     * @param name 教师名
     * @return TeacherEntity
     */
    TeacherEntity findTeacherHasCacheByName(String name);
}
