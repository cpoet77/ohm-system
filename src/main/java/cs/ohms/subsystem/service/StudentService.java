package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.StudentEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 2020/5/23 1:15
 *
 * @author _Struggler
 */
public interface StudentService {
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
     * 添加学生
     *
     * @param studentId 学号
     * @param realName  姓名
     * @param sex       性别
     * @param email     邮箱地址
     * @param phone     手机号
     * @param classId   班级id
     * @return true|false
     */
    @Transactional
    boolean insertStudent(String studentId, String realName, Character sex, String email, String phone, Integer classId);

    /**
     * 更新学生信息
     *
     * @param userId   用户id
     * @param realName 姓名
     * @param sex      性别
     * @param email    邮箱地址
     * @param phone    手机号
     * @param classId  班级id
     * @return true|false
     */
    boolean updateStudent(Integer userId, String realName, Character sex, String email, String phone, Integer classId);

    /**
     * @param student Student
     * @return true or false
     */
    boolean saveStudent(StudentEntity student);


    /**
     * 根据班级查询学生信息
     *
     * @param classId 班级id
     * @param page    页号
     * @param size    数量
     * @return ResponseResult
     */
    ResponseResult getStudentListByClassAndPage(Integer classId, Integer page, Integer size);

    /**
     * 根据专业查询学生信息
     *
     * @param majorId 专业id
     * @param page    页号
     * @param size    数量
     * @return ResponseResult
     */
    ResponseResult getStudentListByMajorAndPage(Integer majorId, Integer page, Integer size);

    /**
     * 根据学院查询学生信息
     *
     * @param collegeId 学院id
     * @param page      页号
     * @param size      数量
     * @return ResponseResult
     */
    ResponseResult getStudentListByCollegeAndPage(Integer collegeId, Integer page, Integer size);

    /**
     * 分页查询学生信息
     *
     * @param page 页号
     * @param size 数量
     * @return ResponseResult
     */
    ResponseResult getStudentListByPage(Integer page, Integer size);
}
