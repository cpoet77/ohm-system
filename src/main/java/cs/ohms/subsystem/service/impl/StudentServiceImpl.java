package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.component.PasswordCMP;
import cs.ohms.subsystem.entity.ClassEntity;
import cs.ohms.subsystem.entity.RoleEntity;
import cs.ohms.subsystem.entity.StudentEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.exception.NSRuntimePostException;
import cs.ohms.subsystem.repository.ClassRepository;
import cs.ohms.subsystem.repository.RoleRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.repository.UserRepository;
import cs.ohms.subsystem.service.ClassService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.service.StudentService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.to.StudentInfoTo;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.vo.StudentVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 2020/5/23 2:01
 *
 * @author _Struggler
 */
@Service("studentService")
@Slf4j
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ClassRepository classRepository;
    private UserService userService;
    private ResourceService resourceService;
    private ClassService classService;
    private PasswordCMP passwordCMP;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository, RoleRepository roleRepository
            , ClassRepository classRepository, UserService userService, ResourceService resourceService, ClassService classService
            , PasswordCMP passwordCMP) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.classRepository = classRepository;
        this.userService = userService;
        this.resourceService = resourceService;
        this.classService = classService;
        this.passwordCMP = passwordCMP;
    }

    @Override
    public boolean testStudentId(String studentId) {
        return Pattern.matches("^[1-9][0-9]{11}$", studentId);
    }

    @Override
    public boolean testStudentId(Collection<String> studentIds) {
        if (studentIds == null || studentIds.isEmpty()) {
            return false;
        }
        for (String studentId : studentIds) {
            if (!testStudentId(studentId)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean insertStudent(String studentId, String realName, Character sex, String email, String phone, Integer classId) {
        Optional<ClassEntity> classOpt = classRepository.findById(classId);
        if (!classOpt.isPresent()) {
            return false;
        }
        try {
            RoleEntity studentRole = roleRepository.findByName(UserService.USER_STUDENT_ROLE);
            assert studentRole != null;
            String salt = passwordCMP.produceSalt();
            UserEntity user = new UserEntity().setName(userService.createDefaultName(studentId)).setRealName(realName)
                    .setSex(sex).setEmail(email).setPhone(phone).setSalt(salt)
                    .setPassword(passwordCMP.encryptPassword(studentId, salt));
            user.getRoles().add(studentRole);
            studentRepository.save(new StudentEntity().setStudentId(studentId).setClazz(classOpt.get())
                    .setUser(userRepository.save(user)));
            return true;
        } catch (Exception e) {
            log.warn("新增学生失败！");
            throw new NSRuntimePostException(e);
        }
    }

    @Override
    public List<StudentInfoTo> importStudentInfoForTable(Boolean isAdmin, InputStream in) {
        try {
            RoleEntity studentRole = roleRepository.findByName(UserService.USER_STUDENT_ROLE);
            assert studentRole != null;
            List<StudentInfoTo> failList = new ArrayList<>();
            List<StudentInfoTo> studentInfoTos = resourceService.inputStreamToTable(StudentInfoTo.class, in);
            if (studentInfoTos.isEmpty()) {
                return null;
            }
            studentInfoTos.forEach(s -> {
                ClassEntity clazz = classService.addClass(isAdmin ? "admin" : "noAdmin", s.getCollegeName()
                        , s.getMajorName(), s.getClassName());
                if (clazz == null) {
                    failList.add(s);
                    return;
                }
                try {
                    userService.saveUserIsStudent(clazz, studentRole, s);
                } catch (Exception e) {
                    failList.add(s);
                }
            });
            return failList;
        } catch (Exception e) {
            log.warn("导入学生信息失败！", e);
        }
        return null;
    }

    @Override
    public boolean updateStudent(Integer userId, String realName, Character sex, String email, String phone, Integer classId) {
        Optional<ClassEntity> classOpt = classRepository.findById(classId);
        if (!classOpt.isPresent()) {
            return false;
        }
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }
        try {
            UserEntity user = userOpt.get().setRealName(realName).setSex(sex).setEmail(email).setPhone(phone);
            StudentEntity student = user.getStudent().setClazz(classOpt.get());
            userRepository.save(user);
            studentRepository.save(student);
            return true;
        } catch (Exception e) {
            log.warn("更新学生信息失败！ userId : {}, msg : {}", userId, e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public ResponseResult getStudentListByClassAndPage(Integer classId, Integer page, Integer size) {
        try {
            Page<StudentEntity> studentPage = studentRepository.findByClazz_Id(classId, PageRequest.of(page, size));
            List<StudentVo> studentVos = student2StudentVo(studentPage.getContent());
            return (ResponseResult.enSuccess().add("recordsTotal", studentRepository.count())
                    .add("recordsFiltered", studentPage.getTotalElements()).add("data", studentVos));
        } catch (Exception e) {
            log.warn("学生信息查询失败！ msg : {}", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getStudentListByMajorAndPage(Integer majorId, Integer page, Integer size) {
        try {
            Page<StudentEntity> studentPage = studentRepository.findByMajor_Id(majorId, PageRequest.of(page, size));
            List<StudentVo> studentVos = student2StudentVo(studentPage.getContent());
            return (ResponseResult.enSuccess().add("recordsTotal", studentRepository.count())
                    .add("recordsFiltered", studentPage.getTotalElements()).add("data", studentVos));
        } catch (Exception e) {
            log.warn("学生信息查询失败！ msg : {}", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getCourseGroupStudentListByStudentId(Integer courseGroupId, String studentId, Integer page, Integer size) {
        try {
            Page<StudentEntity> studentPage = studentRepository.findByCourseGroupIdAndStudentIdIsLike(courseGroupId
                    , NStringUtil.joint("%{}%", studentId), PageRequest.of(page, size));
            List<StudentVo> studentVos = student2StudentVo(studentPage.getContent());
            return (ResponseResult.enSuccess().add("recordsTotal", studentRepository.countByCourseGroup_Id(courseGroupId))
                    .add("recordsFiltered", studentPage.getTotalElements()).add("data", studentVos));
        } catch (Exception e) {
            log.warn("学生信息查询失败！ msg : {}", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getCourseGroupStudentListByName(Integer courseGroupId, String studentName, Integer page, Integer size) {
        try {
            Page<StudentEntity> studentPage = studentRepository.findByCourseGroupIdAndUserRealNameLike(courseGroupId
                    , NStringUtil.joint("%{}%", studentName), PageRequest.of(page, size));
            List<StudentVo> studentVos = student2StudentVo(studentPage.getContent());
            return (ResponseResult.enSuccess().add("recordsTotal", studentRepository.countByCourseGroup_Id(courseGroupId))
                    .add("recordsFiltered", studentPage.getTotalElements()).add("data", studentVos));
        } catch (Exception e) {
            log.warn("学生信息查询失败！ msg : {}", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getCourseGroupStudentList(Integer courseGroupId, String studentId, String studentName, Boolean isAnd, Integer page, Integer size) {
        try {

            Page<StudentEntity> studentPage = isAnd ? studentRepository.findByCourseGroupIdAndUserRealNameLikeAndStudentIdIsLike(courseGroupId
                    , NStringUtil.joint("%{}%", studentId), NStringUtil.joint("%{}%", studentName), PageRequest.of(page, size))
                    : studentRepository.findByCourseGroupIdAndUserRealNameLikeOrStudentIdIsLike(courseGroupId
                    , NStringUtil.joint("%{}%", studentId), NStringUtil.joint("%{}%", studentName), PageRequest.of(page, size));
            List<StudentVo> studentVos = student2StudentVo(studentPage.getContent());
            return (ResponseResult.enSuccess().add("recordsTotal", studentRepository.countByCourseGroup_Id(courseGroupId))
                    .add("recordsFiltered", studentPage.getTotalElements()).add("data", studentVos));
        } catch (Exception e) {
            log.warn("学生信息查询失败！ msg : {}", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getCourseGroupStudentList(Integer courseGroupId, Integer page, Integer size) {
        try {
            Page<StudentEntity> studentPage = studentRepository.findByCourseGroupId(courseGroupId, PageRequest.of(page, size));
            List<StudentVo> studentVos = student2StudentVo(studentPage.getContent());
            return (ResponseResult.enSuccess().add("recordsTotal", studentRepository.countByCourseGroup_Id(courseGroupId))
                    .add("recordsFiltered", studentPage.getTotalElements()).add("data", studentVos));
        } catch (Exception e) {
            log.warn("学生信息查询失败！ msg : {}", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getStudentListByCollegeAndPage(Integer collegeId, Integer page, Integer size) {
        try {
            Page<StudentEntity> studentPage = studentRepository.findByCollege_Id(collegeId, PageRequest.of(page, size));
            List<StudentVo> studentVos = student2StudentVo(studentPage.getContent());
            return (ResponseResult.enSuccess().add("recordsTotal", studentRepository.count())
                    .add("recordsFiltered", studentPage.getTotalElements()).add("data", studentVos));
        } catch (Exception e) {
            log.warn("学生信息查询失败！ msg : {}", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getStudentListByPage(Integer page, Integer size) {
        try {
            Page<StudentEntity> studentPage = studentRepository.findAll(PageRequest.of(page, size));
            List<StudentVo> studentVos = student2StudentVo(studentPage.getContent());
            return (ResponseResult.enSuccess().add("recordsTotal", studentRepository.count())
                    .add("recordsFiltered", studentPage.getTotalElements()).add("data", studentVos));
        } catch (Exception e) {
            log.warn("学生信息查询失败！ msg : {}", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    /**
     * 将entity转vo
     *
     * @param students 承载studentEntity的容器
     * @return StudentVo for list
     */
    @NotNull
    private List<StudentVo> student2StudentVo(@NotNull Collection<StudentEntity> students) {
        List<StudentVo> studentVos = new ArrayList<>();
        students.forEach(student -> studentVos.add(new StudentVo()
                .setName(student.getUser().getName())
                .setClassId(student.getClazz().getId())
                .setClassName(student.getClazz().getName())
                .setMajorId(student.getMajor().getId())
                .setMajorName(student.getMajor().getName())
                .setCollegeId(student.getCollege().getId())
                .setCollegeName(student.getCollege().getName())
                .setRealName(student.getUser().getRealName())
                .setId(student.getUser().getId())
                .setStudentId(student.getStudentId())
                .setSex(student.getUser().getSex())
                .setEmail(student.getUser().getEmail())
                .setPhone(student.getUser().getPhone())
                .setCountJoinCourseGroup(student.getCourseGroups().size())
        ));
        return studentVos;
    }
}
