// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.HomeworkEntity;
import cs.ohms.subsystem.entity.ResourceEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.repository.*;
import cs.ohms.subsystem.service.HomeworkService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.vo.HomeworkVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
    private StudentRepository studentRepository;
    private PushHomeworkRepository pushHomeworkRepository;

    @Autowired
    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, UserRepository userRepository
            , CourseGroupRepository courseGroupRepository, ResourceService resourceService
            , StudentRepository studentRepository, PushHomeworkRepository pushHomeworkRepository) {
        this.homeworkRepository = homeworkRepository;
        this.userRepository = userRepository;
        this.courseGroupRepository = courseGroupRepository;
        this.resourceService = resourceService;
        this.studentRepository = studentRepository;
        this.pushHomeworkRepository = pushHomeworkRepository;
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
                HomeworkEntity homework = new HomeworkEntity().setTitle(title).setContent(description)
                        .setCourseGroup(courseGroupOpt.get()).setStartTime(startTime).setEndTime(endTime).setState(true);
                if (!resources.isEmpty()) {
                    homework.getResources().addAll(resources);
                }
                homeworkRepository.save(homework);
                return true;
            }
        } catch (Exception e) {
            log.warn("发布作业失败！", e);
        }
        return false;
    }

    @Override
    public HomeworkEntity findByCourseGroupAndId(@NotNull Integer courseGroupId, Integer homeworkId) {
        Optional<HomeworkEntity> homeworkOpt = homeworkRepository.findByCourseGroup_IdAndId(courseGroupId, homeworkId);
        return homeworkOpt.orElse(null);
    }

    @Override
    public List<HomeworkVo> findByCourseGroupAndStateForPage(@NotNull UserEntity user, Boolean isTeacher, Integer courseGroupId, int state, int page, int size) {
        CourseGroupEntity courseGroup = isTeacher ? getCourseGroupByTeacher(user, courseGroupId) : getCourseGroupByStudent(user, courseGroupId);
        if (courseGroup == null) {
            return null;
        }
        try {
            Page<HomeworkEntity> homework;
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "endTime", "startTime");
            switch (state) {
                case 1:
                    homework = homeworkRepository.findByCourseGroupAndStateIsTrueAndEndTimeIsAfter(courseGroup, LocalDateTime.now(), pageable);
                    break;
                case 2:
                    homework = homeworkRepository.findByCourseGroupAndStateIsFalseOrEndTimeIsBefore(courseGroup, LocalDateTime.now(), pageable);
                    break;
                default:
                    homework = homeworkRepository.findByCourseGroup(courseGroup, pageable);
            }
            return homework2vo(homework.getContent());
        } catch (Exception e) {
            log.warn("查询作业失败！", e);
        }
        return null;
    }

    @Override
    public List<HomeworkVo> findByCourseGroupAndNameIsLikeAndStateForPage(@NotNull UserEntity user, Boolean isTeacher, Integer courseGroupId, String name, int state, int page, int size) {
        CourseGroupEntity courseGroup = isTeacher ? getCourseGroupByTeacher(user, courseGroupId) : getCourseGroupByStudent(user, courseGroupId);
        if (courseGroup == null) {
            return null;
        }
        try {
            Page<HomeworkEntity> homework;
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "endTime", "startTime");
            switch (state) {
                case 1:
                    homework = homeworkRepository.findByCourseGroupAndTitleIsLikeAndStateIsTrueAndEndTimeIsAfter(courseGroup
                            , NStringUtil.joint("%{}%", name), LocalDateTime.now(), pageable);
                    break;
                case 2:
                    homework = homeworkRepository.findByCourseGroupAndTitleIsLikeAndStateIsFalseOrEndTimeIsBefore(courseGroup
                            , NStringUtil.joint("%{}%", name), LocalDateTime.now(), pageable);
                    break;
                default:
                    homework = homeworkRepository.findByCourseGroupAndTitleIsLike(courseGroup, NStringUtil.joint("%{}%", name)
                            , pageable);
            }
            return homework2vo(homework.getContent());
        } catch (Exception e) {
            log.warn("查询作业失败！", e);
        }
        return null;
    }

    /**
     * 实体转vo
     *
     * @param homework HomeworkEntity for Collection
     * @return HomeworkVo for list
     */
    @NotNull
    private List<HomeworkVo> homework2vo(@NotNull Collection<HomeworkEntity> homework) {
        List<HomeworkVo> homeworkVos = new ArrayList<>();
        homework.forEach(h -> homeworkVos.add(homework2vo(h)));
        return homeworkVos;
    }

    /**
     * 实体转vo
     *
     * @param homework HomeworkEntity
     * @return HomeworkVo
     */
    private HomeworkVo homework2vo(@NotNull HomeworkEntity homework) {
        LocalDateTime endTime = homework.getEndTime();
        LocalDateTime nowTime = LocalDateTime.now();
        boolean state = endTime.isAfter(nowTime) && homework.getState();
        Duration duration = Duration.between(nowTime, endTime);
        long lastDay = duration.toDays();
        long lastHours = duration.toHours() - lastDay * 24;
        long lastMin = duration.toMinutes() - duration.toHours() * 60;
        return (new HomeworkVo().setId(homework.getId())
                .setCourseGroupId(homework.getCourseGroup().getId())
                .setTitle(homework.getTitle())
                .setState(state)
                .setStartTime(homework.getStartTime())
                .setEndTime(endTime)
                .setLastDay(lastDay)
                .setLastHour(lastHours)
                .setLastMin(lastMin)
                .setCountStudent(studentRepository.countByCourseGroup_Id(homework.getCourseGroup().getId()))
                .setCountPush(pushHomeworkRepository.countByHomework(homework)));
    }

    /**
     * 获取教师课群
     *
     * @param user          用户实体
     * @param courseGroupId 课群id
     * @return CourseGroupEntity
     */
    @Nullable
    private CourseGroupEntity getCourseGroupByTeacher(@NotNull UserEntity user, Integer courseGroupId) {
        Optional<UserEntity> userOpt = userRepository.findById(user.getId());
        if (!userOpt.isPresent()) {
            return null;
        }
        Optional<CourseGroupEntity> courseGroupOpt = courseGroupRepository.findByTeacherAndId(user.getTeacher(), courseGroupId);
        return courseGroupOpt.orElse(null);
    }

    /**
     * 获取学生加入的课群实体
     *
     * @param user          用户实体
     * @param courseGroupId 课群id
     * @return CourseGroupEntity
     */
    @Nullable
    private CourseGroupEntity getCourseGroupByStudent(@NotNull UserEntity user, Integer courseGroupId) {
        Optional<UserEntity> userOpt = userRepository.findById(user.getId());
        if (!userOpt.isPresent()) {
            return null;
        }
        Optional<CourseGroupEntity> courseGroupOpt = courseGroupRepository.findByStudentAndId(user.getStudent(), courseGroupId);
        return courseGroupOpt.orElse(null);
    }
}
