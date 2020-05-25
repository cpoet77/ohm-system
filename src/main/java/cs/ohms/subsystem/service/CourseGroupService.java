package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CourseGroupEntity;

import java.io.InputStream;
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
     * 导入课群信息
     *
     * @param in InputStream
     * @return ResponseResult
     */
    ResponseResult importCourseGroupInfo(InputStream in);

    /**
     * 保存课群信息
     *
     * @param courseGroup CourseGroupEntity
     * @return true | false
     */
    boolean saveCourseGroup(CourseGroupEntity courseGroup);
}
