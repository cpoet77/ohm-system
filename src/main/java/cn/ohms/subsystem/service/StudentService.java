// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/6.
package cn.ohms.subsystem.service;

import cs.ohmsubsystem.common.ResponseResult;
import cs.ohmsubsystem.dto.UserDto;

/**
 * StudentService
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface StudentService {
    /**
     * 学生注册
     *
     * @param userDto    UserDto
     * @param studentDto StudentDto
     * @param password   password
     * @return ResponseResult
     */
    ResponseResult register(UserDto userDto, StudentDto studentDto, String password);
}
