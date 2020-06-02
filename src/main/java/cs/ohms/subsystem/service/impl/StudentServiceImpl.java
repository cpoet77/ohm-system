package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.entity.StudentEntity;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.service.StudentService;
import cs.ohms.subsystem.viewobject.StudentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public List<StudentVo> findVoAll() {
        List<StudentEntity> studentList = studentRepository.findAll();
        List<StudentVo> studentVos = new ArrayList<>();

        studentList.forEach(studentEntity -> {
            studentVos.add(new StudentVo()
                    .setName(studentEntity.getUser().getName())
                    .setId(studentEntity.getUser().getId())
                    .setRealName(studentEntity.getUser().getRealName())
                    .setStudentId(studentEntity.getStudentId())
                    .setSex(studentEntity.getUser().getSex())
                    .setClassId(studentEntity.getClazz().getId())
                    .setClassName(studentEntity.getClazz().getName())
                    .setCollegeId(studentEntity.getCollege().getId())
                    .setCollegeName(studentEntity.getCollege().getName())
                    .setMajorId(studentEntity.getMajor().getId())
                    .setMajorName(studentEntity.getMajor().getName())
            );
        });
        return studentVos;
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
    public Boolean saveStudent(StudentEntity student) {
        try {
            studentRepository.save(student);
            return true;
        } catch (Exception e) {
            log.error("保存学生信息失败！msg : {}", e.getLocalizedMessage());
        }
        return false;
    }
}
