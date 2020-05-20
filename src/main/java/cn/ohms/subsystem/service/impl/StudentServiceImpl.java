// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/6.
package cn.ohms.subsystem.service.impl;

import cs.ohmsubsystem.common.ResponseResult;
import cs.ohmsubsystem.common.scene.RegisterScene;
import cs.ohmsubsystem.component.LeafO2oCMP;
import cs.ohmsubsystem.dto.UserDto;
import cs.ohmsubsystem.entity.StudentEntity;
import cs.ohmsubsystem.service.StudentService;
import cs.ohmsubsystem.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * StudentService实现
 * @see cs.ohmsubsystem.service.StudentService
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    // 学生初始角色
    public final static String STUDENT_ROLE = "student";

    private UserService userService;

    private LeafO2oCMP leafO2oCMP;

    @Autowired
    public StudentServiceImpl(UserService userService, LeafO2oCMP leafO2oCMP) {
        this.userService = userService;
        this.leafO2oCMP = leafO2oCMP;
    }

    @Override
    public ResponseResult register(UserDto userDto, @NotNull StudentDto studentDto, String password) {
        StudentEntity student = new StudentEntity();
        leafO2oCMP.convert(studentDto, student, RegisterScene.class);
        /* 保存成功的情况下，发送激活邮件 */
        if(userService.register(userDto, STUDENT_ROLE, student, password, false)){
            return ResponseResult.enSuccess();
        }
        return ResponseResult.enError();
    }
}
