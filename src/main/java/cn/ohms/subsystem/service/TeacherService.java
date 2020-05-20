// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/6.
package cn.ohms.subsystem.service;

import cs.ohmsubsystem.common.ResponseResult;
import cs.ohmsubsystem.dto.UserDto;

/**
 * TeacherService
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface TeacherService {
    /**
     * 教师注册
     *
     * @param userDto    UserDto
     * @param teacherDto TeacherDto
     * @param password   密码
     * @return ResponseResult
     */
    ResponseResult register(UserDto userDto, TeacherDto teacherDto, String password);
}
