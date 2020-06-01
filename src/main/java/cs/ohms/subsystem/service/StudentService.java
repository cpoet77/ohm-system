package cs.ohms.subsystem.service;

import cs.ohms.subsystem.entity.StudentEntity;
import cs.ohms.subsystem.viewobject.StudentVo;

import java.util.List;

/**
 * 2020/5/23 1:15
 *
 * @author _Struggler
 */
public interface StudentService {
    /**
     * 获取所有学生信息
     *
     * @return StudentVo for List
     */
    List<StudentVo> findVoAll();

    /**
     * @param student Student
     * @return true or false
     */
    Boolean saveStudent(StudentEntity student);
}
