// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/25.
package cn.ohms.subsystem.service.impl;

import cs.ohmsubsystem.common.scene.RegisterScene;
import cs.ohmsubsystem.component.LeafO2oCMP;
import cs.ohmsubsystem.component.PasswordCMP;
import cs.ohmsubsystem.dto.UserDto;
import cs.ohmsubsystem.entity.RoleEntity;
import cs.ohmsubsystem.entity.StudentEntity;
import cs.ohmsubsystem.entity.TeacherEntity;
import cs.ohmsubsystem.entity.UserEntity;
import cs.ohmsubsystem.repository.RoleRepository;
import cs.ohmsubsystem.repository.StudentRepository;
import cs.ohmsubsystem.repository.TeacherRepository;
import cs.ohmsubsystem.repository.UserRepository;
import cs.ohmsubsystem.service.AppService;
import cs.ohmsubsystem.service.UserService;
import cs.ohmsubsystem.utils.NStringUtil;
import org.apache.shiro.util.Assert;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * UserService实现
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see cs.ohmsubsystem.service.UserService
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private AppService appService;

    private EmailService emailService;

    private LeafO2oCMP leafO2oCMP;

    private PasswordCMP passwordCMP;

    private UserRepository userRepository;

    private TeacherRepository teacherRepository;

    private StudentRepository studentRepository;

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(AppService appService, EmailService emailService, LeafO2oCMP leafO2oCMP
            , PasswordCMP passwordCMP, UserRepository userRepository, TeacherRepository teacherRepository
            , StudentRepository studentRepository, RoleRepository roleRepository) {
        this.appService = appService;
        this.emailService = emailService;
        this.leafO2oCMP = leafO2oCMP;
        this.passwordCMP = passwordCMP;
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    /*@Cacheable(cacheNames = "user", key = "#userName")*/
    public boolean containUserName(String userName) {
        return userRepository.existsByName(userName);
    }

    @Override
    public String generateActivationUrl(int userId, @NotNull String email) {
        // 用户id
        String userIdBase = Base64Utils.encodeToString(String.valueOf(userId).getBytes());
        // 用户邮箱
        String emailBase = Base64Utils.encodeToString(email.getBytes());
        // 用户发送激活邮件的时间
        String timeBase = Base64Utils.encodeToString(String.valueOf(System.currentTimeMillis()).getBytes());
        return (NStringUtil.joint("{}/user/accountActivation?u={}&e={}&t={}", appService.getDomain(), userIdBase
                , emailBase, timeBase));
    }

    public boolean register(UserDto userDto, String roleName, Object object, String password, boolean isTeacher) {
        RoleEntity role = roleRepository.findByName(roleName);
        Assert.notNull(role);// student角色是必须存在的
        UserEntity user = new UserEntity();
        leafO2oCMP.convert(userDto, user, RegisterScene.class);
        System.out.println(userDto);
        System.out.println(user.getName());
        String salt = passwordCMP.produceSalt();
        user.setSalt(salt);
        user.setPassword(passwordCMP.encryptPassword(password, salt));
        user.getRoles().add(role);
        userRepository.save(user);
        if (isTeacher) {
            TeacherEntity teacher = (TeacherEntity) object;
            teacher.setUserId(user.getId());
            teacherRepository.save(teacher);
        } else {
            StudentEntity student = (StudentEntity) object;
            student.setUserId(user.getId());
            studentRepository.save(student);
        }
        return (emailService.sendActivationEmail(user.getEmail(), generateActivationUrl(user.getId()
                , user.getEmail()), isTeacher));
    }

    @Override
    public boolean accountActivation(int userId, String email) {
        UserEntity user = userRepository.findByIdAndEmail(userId, email);
        user.setActivation(true);
        return userRepository.save(user).getActivation();
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Map<String, Object> findUserProfileInfo(int userId, String userName) {
        return findUserProfileBaseInfo(userId, userName);
    }

    @Override
    public Map<String, Object> findUserProfileBaseInfo(int userId, String userName) {
        UserEntity other = userRepository.findByIdAndName(userId, userName);
        if(other == null){
            return null;
        }
        Map<String, Object> info = new HashMap<>();
        info.put("user", other);
        info.put("likes", 10);
        info.put("collects", 13);
        info.put("posts", 50);
        return info;
    }
}
