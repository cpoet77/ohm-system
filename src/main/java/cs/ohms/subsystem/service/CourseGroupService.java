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
     * @param start  起点
     * @param length 长度
     * @return ResponseResult
     */
    ResponseResult getCourseGroupByPage(int start, int length);

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
     * 保存课群信息
     *
     * @param courseGroup CourseGroupEntity
     * @return true | false
     */
    boolean saveCourseGroup(CourseGroupEntity courseGroup);

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
