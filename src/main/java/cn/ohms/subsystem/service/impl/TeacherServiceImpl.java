// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/6.
package cn.ohms.subsystem.service.impl;

import cs.ohmsubsystem.common.ResponseResult;
import cs.ohmsubsystem.component.LeafO2oCMP;
import cs.ohmsubsystem.dto.UserDto;
import cs.ohmsubsystem.entity.TeacherEntity;
import cs.ohmsubsystem.service.TeacherService;
import cs.ohmsubsystem.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * TeacherService实现
 * @see cs.ohmsubsystem.service.TeacherService
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
    public final static String TEACHER_ROLE = "teacher";

    private UserService userService;

    private LeafO2oCMP leafO2oCMP;

    public TeacherServiceImpl(UserService userService, LeafO2oCMP leafO2oCMP) {
        this.userService = userService;
        this.leafO2oCMP = leafO2oCMP;
    }

    @Override
    public ResponseResult register(UserDto userDto, @NotNull TeacherDto teacherDto, String password) {
        TeacherEntity teacher = new TeacherEntity();
        leafO2oCMP.convert(teacherDto, teacher);
        if(userService.register(userDto, TEACHER_ROLE, teacher, password, true)){
            return ResponseResult.enSuccess();
        }
        return ResponseResult.enError();
    }
}
