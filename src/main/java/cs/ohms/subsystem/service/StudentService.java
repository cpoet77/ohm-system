package cs.ohms.subsystem.service;

import cs.ohms.subsystem.entity.StudentEntity;
import cs.ohms.subsystem.viewobject.StudentVo;

import java.util.Collection;
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
     * 验证学号是否满足要求
     *
     * @param studentId 学号
     * @return true|false
     */
    boolean testStudentId(String studentId);

    /**
     * 批量验证学号是否满足要求
     *
     * @param studentIds studentIds
     * @return true|false
     */
    boolean testStudentId(Collection<String> studentIds);

    /**
     * @param student Student
     * @return true or false
     */
    Boolean saveStudent(StudentEntity student);
}
