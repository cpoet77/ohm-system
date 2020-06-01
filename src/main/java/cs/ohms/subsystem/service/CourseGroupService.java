package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CourseGroupEntity;

import java.util.List;

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
     * 分页获取课程列表
     *
     * @param start  起点
     * @param length 长度
     * @return ResponseResult
     */
    ResponseResult getCourseGroupByPage(int start, int length);

    /**
     * 保存课群信息
     *
     * @param courseGroup CourseGroupEntity
     * @return true | false
     */
    boolean saveCourseGroup(CourseGroupEntity courseGroup);

    /**
     * 保存课群信息
     *
     * @param id              课群id,为null时是添加，否则是更新
     * @param courseGroupName 课群名
     * @param TeacherRealName 教师真实名称
     * @param description     课程名称
     * @param state           课群状态
     * @return true|false
     */
    boolean saveCourseGroup(Integer id, String TeacherRealName, String courseGroupName, String description, String state);

    /**
     * 删除课群信息
     *
     * @param id 课群id
     * @return true|false
     */
    boolean deleteCourseGroup(Integer id);

}
