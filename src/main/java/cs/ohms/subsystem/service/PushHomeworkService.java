// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.entity.PushHomeworkEntity;
import cs.ohms.subsystem.entity.UserEntity;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface PushHomeworkService {
    /**
     * 保存学生提交的作业
     *
     * @param user        学生的user对象
     * @param homeworkId  作业id
     * @param description 提交的作业描述
     * @param files       提交的附件
     * @return true|false
     */
    boolean savePushHomework(UserEntity user, Integer homeworkId, String description, String files);

    /**
     * 根据id查找
     *
     * @param pushHomeworkId 提交作业id
     * @return PushHomeworkEntity
     */
    PushHomeworkEntity findById(Integer pushHomeworkId);
}
