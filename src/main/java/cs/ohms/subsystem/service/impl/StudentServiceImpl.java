package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.component.PasswordCMP;
import cs.ohms.subsystem.entity.MajorEntity;
import cs.ohms.subsystem.entity.RoleEntity;
import cs.ohms.subsystem.entity.StudentEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.repository.RoleRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.service.MajorService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.service.StudentService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.tableobject.StudentTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 2020/5/23 2:01
 *
 * @author _Struggler
 */
@Service("studentService")
@Slf4j
public class StudentServiceImpl implements StudentService {
    private UserService userService;
    private StudentRepository studentRepository;
    private ResourceService resourceService;
    private PasswordCMP passwordCMP;
    private RoleRepository roleRepository;
    private MajorService majorService;

    @Autowired
    public StudentServiceImpl (UserService userService, StudentRepository studentRepository,
                               ResourceService resourceService, PasswordCMP passwordCMP,
                               RoleRepository roleRepository, MajorService majorService) {
        this.userService = userService;
        this.studentRepository = studentRepository;
        this.resourceService = resourceService;
        this.passwordCMP = passwordCMP;
        this.roleRepository = roleRepository;
        this.majorService = majorService;
    }


    @Override
    public List<StudentEntity> findAll ( ) {
        return studentRepository.findAll();
    }

    @Override
    public ResponseResult importStudentInfo (InputStream inputStream) {
        RoleEntity role = roleRepository.findByName(UserService.USER_STUDENT_ROLE);
        if (null == role) {
            log.error("请确保学生角色存在！");
            return ResponseResult.enError();
        }
        try {
            List<StudentTo> errList = new ArrayList<>();
            List<StudentTo> studentTos = resourceService.inputStreamToTable(StudentTo.class, inputStream);
            studentTos.forEach(studentTo -> {
                String salt = passwordCMP.produceSalt();
                MajorEntity major = majorService.findMajorHashCacheByName(studentTo.getMajor());
                if(null != major ){
                    UserEntity user = new UserEntity().setName(userService.createDefaultName(studentTo.getStudentId()))
                            .setRealName(studentTo.getRealName()).setPassword(passwordCMP.encryptPassword(studentTo.getPassword(), salt))
                            .setSalt(salt).setSex("男".equals(studentTo.getSex()) ? 'M' : 'F');
                    user.getRoles().add(role);
                    if (userService.saveUser(user)) {
                        StudentEntity student = new StudentEntity().setStudentId(studentTo.getStudentId()).setUser(user).setMajor(major);
                        if (!saveStudent(student)) {
                            errList.add(studentTo);
                        }
                    } else {
                        errList.add(studentTo);
                    }
                }

            });
            int count = studentTos.size();
            int fail = errList.size();
            int success = count - fail;
            return (ResponseResult.enSuccess().add("count", count).add("success", success)
                    .add("fail", fail).add("errList", errList));

        } catch (Exception e) {
            log.error("导入表格失败！");
        }
        return ResponseResult.enError();
    }

    @Override
    public Boolean saveStudent (StudentEntity student) {
        try {
            studentRepository.save(student);
            return true;
        } catch (Exception e) {
            log.error("保存学生信息失败！msg : {}", e.getLocalizedMessage());
        }
        return false;
    }
}
