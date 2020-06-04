package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CourseGroupEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 2020/5/23 23:12
 *
 * @author LRC
 */
public interface CourseGroupService {
    /**
     * 获取所有课群信息
     *
     * @return CourseGroup for list
     */
    List<CourseGroupEntity> findAll();

    /**
     * 根据id查询课群信息
     *
     * @param id 课群id
     * @return CourseGroupEntity
     */
    CourseGroupEntity findById(Integer id);

    /**
     * 分页获取课程列表
     *
     * @param page 页号
     * @param size 数量
     * @return ResponseResult
     */
    ResponseResult getCourseGroupForPage(int page, int size);

    /**
     * 根据教职工号Like分页查询课群
     *
     * @param teacherId 教职工号
     * @param page      页号
     * @param size      数量
     * @return ResponseResult
     */
    ResponseResult getCourseGroupByTeacherForPage(String teacherId, int page, int size);

    /**
     * 根据课群名Like分页查询课群
     *
     * @param courseGroupName 课群名
     * @param page            页号
     * @param size            数量
     * @return ResponseResult
     */
    ResponseResult getCourseGroupByNameForPage(String courseGroupName, int page, int size);

    /**
     * 根据课群名Like且教职工号Like查询课群
     *
     * @param teacherId       教职工号
     * @param courseGroupName 课群名
     * @param page            页号
     * @param size            数量
     * @return ResponseResult
     */
    ResponseResult getCourseGroupByTeacherAndNameForPage(String teacherId, String courseGroupName, int page, int size);

    /**
     * 根据课群名Like或教职工号Like查询课群
     *
     * @param teacherId       教职工号
     * @param courseGroupName 课群名
     * @param page            页号
     * @param size            数量
     * @return ResponseResult
     */
    ResponseResult getCourseGroupByTeacherOrNameForPage(String teacherId, String courseGroupName, int page, int size);

    /**
     * 添加课群
     *
     * @param courseGroupName 课群名
     * @param teacherId       教职工号
     * @param description     课群简介
     * @param studentIds      加入课群的学生们
     * @return true|false
     */
    boolean addCourseGroup(String courseGroupName, String teacherId, String description, Set<String> studentIds);

    /**
     * 由班级快速创建课群
     *
     * @param classId         班级id
     * @param courseGroupName 课群名
     * @param teacherId       教职工号
     * @param description     简介
     * @return true|false
     */
    boolean addCourseGroupForClass(Integer classId, String courseGroupName, String teacherId, String description);

    /**
     * 保存课群信息
     *
     * @param courseGroup CourseGroupEntity
     * @return true | false
     */
    boolean saveCourseGroup(CourseGroupEntity courseGroup);

    /**
     * 更新课群信息
     *
     * @param courseGroupId   课群id
     * @param courseGroupName 课群名
     * @param teacherId       教职工号
     * @param description     介绍
     * @return true|false
     */
    boolean updateCourseGroup(Integer courseGroupId, String courseGroupName, String teacherId, String description);

    /**
     * 删除课群信息
     *
     * @param id 课群id
     * @return true|false
     */
    boolean deleteCourseGroup(Integer id);

    /**
     * 将学生们添加到课群中
     *
     * @param courseGroupId 课群id
     * @param studentIds    加入课群的学生学号
     * @return true|false
     */
    boolean addStudent2CourseGroup(Integer courseGroupId, Collection<String> studentIds);

    /**
     * 从课群中移除学生
     *
     * @param courseGroupId 课群id
     * @param studentId     学号
     * @return true|false
     */
    boolean removeStudentByStudentId(Integer courseGroupId, String studentId);
}
