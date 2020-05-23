package cn.ohms.subsystem.service;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.entity.StudentEntity;

import java.io.InputStream;
import java.util.List;

/**
 * @auther _Struggler
 * 2020/5/23 1:15
 */
public interface StudentService {
  /**
   * 获取所有学生信息
   *
   * @param: void
   * @return: StudentEntiry for List
   */
    List<StudentEntity> findAll();

    /**
     * 导入学生信息
     *
     * @param: inputStream  InputStream
     * @return: ResponseResult
     */
    ResponseResult importStudentInfo(InputStream inputStream);

    /**
     *
     *
     * @param: student Student
     * @return:  true or false
     */
    Boolean saveStudent(StudentEntity student);

}
