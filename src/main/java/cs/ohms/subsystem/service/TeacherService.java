// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.TeacherEntity;

import java.io.InputStream;
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
     * 根据教师真实姓名获得教师信息
     *
     * @param realName String
     * @return TeacherEntity
     */
    TeacherEntity findTeacherByRealName(String realName);
    /**
     * 导入教师信息
     *
     * @param in InputStream
     * @return ResponseResult
     */
    ResponseResult importTeacherInfo(InputStream in);

    /**
     * 保存教师
     *
     * @param teacher TeacherEntity
     * @return true|false
     */
    boolean saveTeacher(TeacherEntity teacher);

    /**
     * 根据name查询teacher
     * <p>在缓存有效期内，查找cache - database</p>
     *
     * @param name 教师名
     * @return TeacherEntity
     */
    TeacherEntity findTeacherHasCacheByName(String name);
}
