// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.entity.UserEntity;

import java.time.LocalDateTime;

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
}
