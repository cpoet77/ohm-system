// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.component.PasswordCMP;
import cs.ohms.subsystem.entity.RoleEntity;
import cs.ohms.subsystem.entity.TeacherEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.entity.middle.UserRoleEntity;
import cs.ohms.subsystem.exception.NSRuntimePostException;
import cs.ohms.subsystem.repository.CourseGroupRepository;
import cs.ohms.subsystem.repository.RoleRepository;
import cs.ohms.subsystem.repository.TeacherRepository;
import cs.ohms.subsystem.repository.UserRepository;
import cs.ohms.subsystem.repository.middle.UserRoleRepository;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.service.TeacherService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.to.TeacherInfoTo;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.vo.TeacherVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("teacherService")
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private UserService userService;
    private TeacherRepository teacherRepository;
    private UserRepository userRepository;
    private CourseGroupRepository courseGroupRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordCMP passwordCMP;
    private ResourceService resourceService;

    @Autowired
    public TeacherServiceImpl(UserService userService, TeacherRepository teacherRepository, UserRepository userRepository
            , CourseGroupRepository courseGroupRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository
            , PasswordCMP passwordCMP, ResourceService resourceService) {
        this.userService = userService;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.courseGroupRepository = courseGroupRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordCMP = passwordCMP;
        this.resourceService = resourceService;
    }

    @Override
    public List<TeacherEntity> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public ResponseResult findTeacherByTeacherNameAndPage(Integer start, Integer length, String findTeacherName) {
        Page<UserEntity> users = userRepository.findByRealNameIsLikeAndTeacherIsNotNull(NStringUtil.joint("%{}%", findTeacherName)
                , PageRequest.of(resourceService.calculatePageNum(start, length), length));
        List<TeacherVo> teacherVos = new ArrayList<>();
        RoleEntity teacherRole = roleRepository.findByName(UserService.USER_TEACHING_SECRETARY_ROLE);
        assert teacherRole != null;/* 永远成立的断言 */
        users.forEach(user -> teacherVos.add(new TeacherVo().setId(user.getId())
                .setUsername(user.getName()).setRealName(user.getRealName())
                .setEmail(user.getEmail()).setPhone(user.getPhone())
                .setSex(user.getSex().equals('M') ? '男' : '女')
                .setTeacherId(user.getTeacher().getTeacherId())
                .setCountCourseGroup(courseGroupRepository.countByTeacher_TeacherId(user.getTeacher().getTeacherId()))
                .setIsTeachingSecretary(user.getRoles().contains(teacherRole))));
        return (ResponseResult.enSuccess().add("recordsTotal", teacherRepository.count())
                .add("recordsFiltered", users.getTotalElements()).add("data", teacherVos));
    }

    @Override
    public ResponseResult findTeacherByPage(Integer start, Integer length) {
        Page<TeacherEntity> teachers = teacherRepository.findAll(PageRequest.of(resourceService.calculatePageNum(start, length), length));
        List<TeacherVo> teacherVos = new ArrayList<>();
        RoleEntity role = roleRepository.findByName(UserService.USER_TEACHING_SECRETARY_ROLE);
        assert role != null;
        teachers.forEach(teacher -> teacherVos.add(new TeacherVo().setId(teacher.getUser().getId())
                .setUsername(teacher.getUser().getName())
                .setRealName(teacher.getUser().getRealName())
                .setTeacherId(teacher.getTeacherId())
                .setEmail(teacher.getUser().getEmail())
                .setPhone(teacher.getUser().getPhone())
                .setSex(teacher.getUser().getSex().equals('M') ? '男' : '女')
                .setCountCourseGroup(courseGroupRepository.countByTeacher_TeacherId(teacher.getTeacherId()))
                .setIsTeachingSecretary(teacher.getUser().getRoles().contains(role))));
        return (ResponseResult.enSuccess().add("recordsTotal", teacherRepository.count())
                .add("recordsFiltered", teachers.getTotalElements()).add("data", teacherVos));
    }

    @Override
    public boolean saveTeacher(Boolean currentUserIsAdmin, Integer userId, String teacherId, String realName
            , Character sex, String phone, String email) {
        if (userId != null) {
            return userService.updateUserById(currentUserIsAdmin, userId, realName, sex, email, phone);
        }
        try {
            RoleEntity teacherRole = roleRepository.findByName(UserService.USER_TEACHER_ROLE);
            assert teacherRole != null;/* 必须存在的角色 */
            String salt = passwordCMP.produceSalt();/* 密码加密的盐 */
            UserEntity user = new UserEntity().setRealName(realName).setSex(sex).setPhone(phone).setEmail(email)
                    .setSalt(salt).setPassword(passwordCMP.encryptPassword(teacherId, salt))
                    .setName(userService.createDefaultName(teacherId));
            user.getRoles().add(teacherRole);
            teacherRepository.save(new TeacherEntity().setTeacherId(teacherId).setUser(userRepository.save(user)));
            return true;
        } catch (Exception e) {
            log.warn("新增教师失败！msg : {}", e.getLocalizedMessage());
            throw new NSRuntimePostException(e);
        }
    }

    @Override
    public List<TeacherInfoTo> importTeacherInfoForTable(InputStream in) {
        try {
            List<TeacherInfoTo> failList = new ArrayList<>();
            RoleEntity teacherRole = roleRepository.findByName(UserService.USER_TEACHER_ROLE);
            assert teacherRole != null;
            List<TeacherInfoTo> teacherInfoTos = resourceService.inputStreamToTable(TeacherInfoTo.class, in);
            if (teacherInfoTos.isEmpty()) {
                return null;
            }
            teacherInfoTos.forEach(t -> {
                try {
                    userService.saveUserIsTeacher(teacherRole, t);
                } catch (Exception e) {
                    failList.add(t);/* 添加失败 */
                }
            });
            return failList;
        } catch (Exception e) {
            log.warn("教师信息导入失败！", e);
        }
        return null;
    }

    @Override
    public boolean changeTeachingSecretaryRole(Integer userId) {
        RoleEntity teachingSecretaryRole = roleRepository.findByName(UserService.USER_TEACHING_SECRETARY_ROLE);
        assert teachingSecretaryRole != null;
        UserRoleEntity.Key userRoleKey = new UserRoleEntity.Key().setUserId(userId).setRoleId(teachingSecretaryRole.getId());
        try {
            if (userRoleRepository.existsById(userRoleKey)) {
                userRoleRepository.deleteById(userRoleKey);
            } else {
                userRoleRepository.save(new UserRoleEntity().setId(userRoleKey));
            }
            return true;
        } catch (Exception e) {
            log.warn("清除或者添加教学秘书权限失败！msg : {}", e.getLocalizedMessage());
        }
        return false;
    }
}
