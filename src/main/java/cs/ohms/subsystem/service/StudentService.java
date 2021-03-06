package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.to.StudentInfoTo;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

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
     * 导入学生信息
     *
     * @param isAdmin 是否是管理员在进行操作
     * @param in      InputStream
     * @return studentInfoTo 失败的信息列表
     */
    List<StudentInfoTo> importStudentInfoForTable(Boolean isAdmin, InputStream in);

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
     * 分页获取课群下的学生
     *
     * @param courseGroupId 课群id
     * @param studentId     用于搜索的学号
     * @param page          页号
     * @param size          数量
     * @return ResponseResult
     */
    ResponseResult getCourseGroupStudentListByStudentId(Integer courseGroupId, String studentId, Integer page, Integer size);

    /**
     * 分页获取课群下的学生
     *
     * @param courseGroupId 课群id
     * @param studentName   用于搜索的学生姓名
     * @param page          页号
     * @param size          数量
     * @return ResponseResult
     */
    ResponseResult getCourseGroupStudentListByName(Integer courseGroupId, String studentName, Integer page, Integer size);

    /**
     * 分页获取课群下的学生
     *
     * @param courseGroupId 课群id
     * @param studentId     用于搜索的学号
     * @param studentName   用于搜索的学生姓名
     * @param isAnd         搜索条件关系,非and即or
     * @param page          页号
     * @param size          数量
     * @return ResponseResult
     */
    ResponseResult getCourseGroupStudentList(Integer courseGroupId, String studentId, String studentName, Boolean isAnd, Integer page, Integer size);

    /**
     * 分页获取课群下的学生
     *
     * @param courseGroupId 课群id
     * @param page          页号
     * @param size          数量
     * @return ResponseResult
     */
    ResponseResult getCourseGroupStudentList(Integer courseGroupId, Integer page, Integer size);

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
