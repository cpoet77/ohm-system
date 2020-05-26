package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.StudentEntity;

import java.io.InputStream;
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
     * @return StudentEntity for List
     */
    List<StudentEntity> findAll();

    /**
     * 导入学生信息
     *
     * @param inputStream  InputStream
     * @return ResponseResult
     */
    ResponseResult importStudentInfo(InputStream inputStream);

    /**
     * @param student Student
     * @return true or false
     */
    Boolean saveStudent(StudentEntity student);
}
