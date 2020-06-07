// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.entity.*;
import cs.ohms.subsystem.repository.CourseGroupRepository;
import cs.ohms.subsystem.repository.HomeworkRepository;
import cs.ohms.subsystem.repository.PushHomeworkRepository;
import cs.ohms.subsystem.repository.UserRepository;
import cs.ohms.subsystem.service.PushHomeworkService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.utils.NStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("pushHomeworkService")
@Slf4j
public class PushHomeworkServiceImpl implements PushHomeworkService {
    private UserRepository userRepository;
    private PushHomeworkRepository pushHomeworkRepository;
    private CourseGroupRepository courseGroupRepository;
    private HomeworkRepository homeworkRepository;
    private ResourceService resourceService;

    @Autowired
    public PushHomeworkServiceImpl(UserRepository userRepository, PushHomeworkRepository pushHomeworkRepository
            , CourseGroupRepository courseGroupRepository, HomeworkRepository homeworkRepository, ResourceService resourceService) {
        this.userRepository = userRepository;
        this.pushHomeworkRepository = pushHomeworkRepository;
        this.courseGroupRepository = courseGroupRepository;
        this.homeworkRepository = homeworkRepository;
        this.resourceService = resourceService;
    }

    @Override
    public boolean savePushHomework(@NotNull UserEntity user, Integer homeworkId, String description, String files) {
        Optional<UserEntity> userOpt = userRepository.findById(user.getId());
        Optional<HomeworkEntity> homeworkOpt = homeworkRepository.findById(homeworkId);
        List<ResourceEntity> resources = resourceService.findAllByResourceId(files);
        if (!userOpt.isPresent() || !homeworkOpt.isPresent() || (resources.isEmpty() && NStringUtil.isEmpty(description))) {
            return false;
        }
        HomeworkEntity homework = homeworkOpt.get();
        StudentEntity student = user.getStudent();
        try {
            if (courseGroupRepository.findByStudentAndId(student, homework.getCourseGroup().getId()).isPresent()) {
                PushHomeworkEntity pushHomework = new PushHomeworkEntity().setText(description).setHomework(homework)
                        .setStudent(student);
                if (!resources.isEmpty()) {
                    pushHomework.getResources().addAll(resources);
                }
                pushHomeworkRepository.save(pushHomework);
                return true;
            }
        } catch (Exception e) {
            log.warn("上传作业失败， studentId : {} ,homeworkId : {}", homeworkId, student.getStudentId());
        }
        return false;
    }

    @Override
    public PushHomeworkEntity findById(Integer pushHomeworkId) {
        return pushHomeworkRepository.findById(pushHomeworkId).orElse(null);
    }

    @Override
    public PushHomeworkEntity findByStudentAndHomework(@NotNull UserEntity user, Integer homeworkId) {
        Optional<UserEntity> userOpt = userRepository.findById(user.getId());
        return userOpt.flatMap(userEntity -> pushHomeworkRepository.findByStudentAndHomework_Id(userEntity.getStudent()
                , homeworkId)).orElse(null);

    }

    @Override
    public boolean saveScore(@NotNull UserEntity user, Integer pushHomeworkId, Integer score, String assess) {
        Optional<UserEntity> userOpt = userRepository.findById(user.getId());
        if (!userOpt.isPresent()) {
            return false;
        }
        Optional<PushHomeworkEntity> pushHomeworkOpt = pushHomeworkRepository.findById(pushHomeworkId);
        if (!pushHomeworkOpt.isPresent()) {
            return false;
        }
        PushHomeworkEntity pushHomework = pushHomeworkOpt.get();
        try {
            if (pushHomework.getHomework().getCourseGroup().getTeacher().getTeacherId().equals(user.getTeacher().getTeacherId())) {
                pushHomeworkRepository.save(pushHomework.setScore(score).setAssess(assess));
                return true;
            }
        } catch (Exception e) {
            log.warn("评价保存失败！", e);
        }
        return false;
    }
}
