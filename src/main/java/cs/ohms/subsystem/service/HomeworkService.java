// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.entity.HomeworkEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.vo.HomeworkVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface HomeworkService {
    /**
     * 添加作业
     *
     * @param user          当前操作用户
     * @param courseGroupId 课群id
     * @param title         标题
     * @param description   描述
     * @param files         附件
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return true|false
     */
    boolean addHomework(UserEntity user, Integer courseGroupId, String title, String description, String files
            , LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查找作业
     *
     * @param courseGroupId 课群id
     * @param homeworkId    作业id
     * @return 作业实体
     */
    HomeworkEntity findByCourseGroupAndId(Integer courseGroupId, Integer homeworkId);

    /**
     * 查询课群下的作业信息
     *
     * @param user          user
     * @param isTeacher     是否时教师
     * @param courseGroupId 课群id
     * @param state         状态,0\1\2
     * @param page          页号
     * @param size          数量
     * @return HomeworkVo for list
     */
    List<HomeworkVo> findByCourseGroupAndStateForPage(UserEntity user, Boolean isTeacher, Integer courseGroupId, int state, int page, int size);

    /**
     * 查询课群下的作业，name is like
     *
     * @param user          user
     * @param isTeacher     是否是教师
     * @param courseGroupId 课群id
     * @param name          课群名
     * @param state         状态
     * @param page          页号
     * @param size          数量
     * @return HomeworkVo for list
     */
    List<HomeworkVo> findByCourseGroupAndNameIsLikeAndStateForPage(UserEntity user, Boolean isTeacher, Integer courseGroupId, String name, int state, int page, int size);
}
