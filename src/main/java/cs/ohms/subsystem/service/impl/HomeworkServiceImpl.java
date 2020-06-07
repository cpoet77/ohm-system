// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.HomeworkEntity;
import cs.ohms.subsystem.entity.ResourceEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.repository.CourseGroupRepository;
import cs.ohms.subsystem.repository.HomeworkRepository;
import cs.ohms.subsystem.repository.UserRepository;
import cs.ohms.subsystem.service.HomeworkService;
import cs.ohms.subsystem.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("homeworkService")
@Slf4j
public class HomeworkServiceImpl implements HomeworkService {
    private HomeworkRepository homeworkRepository;
    private UserRepository userRepository;
    private CourseGroupRepository courseGroupRepository;
    private ResourceService resourceService;

    @Autowired
    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, UserRepository userRepository
            , CourseGroupRepository courseGroupRepository, ResourceService resourceService) {
        this.homeworkRepository = homeworkRepository;
        this.userRepository = userRepository;
        this.courseGroupRepository = courseGroupRepository;
        this.resourceService = resourceService;
    }

    @Override
    public boolean addHomework(@NotNull UserEntity user, Integer courseGroupId, String title, String description, String files
            , LocalDateTime startTime, LocalDateTime endTime) {
        Optional<UserEntity> userOpt = userRepository.findById(user.getId());
        if (!userOpt.isPresent()) {
            return false;
        }
        Optional<CourseGroupEntity> courseGroupOpt = courseGroupRepository.findByTeacherAndId(userOpt.get().getTeacher(), courseGroupId);
        try {
            if (courseGroupOpt.isPresent()) {
                List<ResourceEntity> resources = resourceService.findAllByResourceId(files);
                if (resources == null) {
                    return false;
                }
                HomeworkEntity homework = new HomeworkEntity().setTitle(title).setContent(description)
                        .setCourseGroup(courseGroupOpt.get()).setStartTime(startTime).setEndTime(endTime).setState(true);
                homework.getResources().addAll(resources);
                homeworkRepository.save(homework);
                return true;
            }
        } catch (Exception e) {
            log.warn("发布作业失败！", e);
        }
        return false;
    }
}
