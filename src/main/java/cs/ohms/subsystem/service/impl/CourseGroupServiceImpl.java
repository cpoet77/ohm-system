package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.*;
import cs.ohms.subsystem.entity.middle.StudentCourseGroupEntity;
import cs.ohms.subsystem.repository.ClassRepository;
import cs.ohms.subsystem.repository.CourseGroupRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.repository.TeacherRepository;
import cs.ohms.subsystem.repository.middle.StudentCourseGroupRepository;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.vo.CourseGroupListVo;
import cs.ohms.subsystem.vo.CourseGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 2020/5/23 23:23
 *
 * @author LRC
 **/
@Service("courseGroupService")
@Slf4j
public class CourseGroupServiceImpl implements CourseGroupService {
    private CourseGroupRepository courseGroupRepository;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private StudentCourseGroupRepository studentCourseGroupRepository;
    private ClassRepository classRepository;

    @Autowired
    public CourseGroupServiceImpl(CourseGroupRepository courseGroupRepository, TeacherRepository teacherRepository
            , StudentRepository studentRepository, StudentCourseGroupRepository studentCourseGroupRepository
            , ClassRepository classRepository) {
        this.courseGroupRepository = courseGroupRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.studentCourseGroupRepository = studentCourseGroupRepository;
        this.classRepository = classRepository;
    }


    @Override
    public List<CourseGroupEntity> findAll() {
        return courseGroupRepository.findAll();
    }

    @Override
    public CourseGroupEntity findById(Integer id) {
        Optional<CourseGroupEntity> courseGroupOpt = courseGroupRepository.findById(id);
        return courseGroupOpt.orElse(null);
    }

    @Override
    public CourseGroupVo findByTeacherAndId(UserEntity user, Integer id) {
        Optional<CourseGroupEntity> courseGroupOpt = courseGroupRepository.findByTeacherAndId(teacherRepository
                .findByUser(user), id);
        return courseGroupOpt.map(this::courseGroupEntity2Vo).orElse(null);
    }

    @Override
    public CourseGroupVo findByStudentAndId(UserEntity user, Integer id) {
        Optional<CourseGroupEntity> courseGroupOpt = courseGroupRepository.findByStudentAndId(studentRepository
                .findByUser(user), id);
        return courseGroupOpt.map(this::courseGroupEntity2Vo).orElse(null);
    }

    @Override
    public ResponseResult getCourseGroupForPage(int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAll(PageRequest.of(page, size));
            return (ResponseResult.enSuccess().add("recordsTotal", courseGroupRepository.count())
                    .add("recordsFiltered", courseGroupPage.getTotalElements())
                    .add("data", courseGroupEntity2Vo(courseGroupPage.getContent())));
        } catch (Exception e) {
            log.warn("???????????????????????????", e);
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getCourseGroupByTeacherForPage(String teacherId, int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAllByTeacher_TeacherIdIsLike(NStringUtil
                    .joint("%{}%", teacherId), PageRequest.of(page, size));
            return (ResponseResult.enSuccess().add("recordsTotal", courseGroupRepository.count())
                    .add("recordsFiltered", courseGroupPage.getTotalElements())
                    .add("data", courseGroupEntity2Vo(courseGroupPage.getContent())));
        } catch (Exception e) {
            log.warn("???????????????????????????", e);
        }
        return ResponseResult.enFail();
    }

    @Override
    public CourseGroupListVo getCourseGroupListByTeacherForPage(UserEntity user, int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAllByTeacher(teacherRepository.findByUser(user)
                    , PageRequest.of(page, size, Sort.Direction.DESC, "createTime"));
            return (new CourseGroupListVo()).setCount(courseGroupPage.getTotalElements()).setPage(courseGroupPage
                    .getTotalPages()).setCourseGroups(courseGroupPage.getContent());
        } catch (Exception e) {
            log.warn("???????????????????????????", e);
        }
        return null;
    }

    @Override
    public CourseGroupListVo getCourseGroupListByStudentForPage(UserEntity user, int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAllByStudent(studentRepository.findByUser(user)
                    , PageRequest.of(page, size, Sort.Direction.DESC, "create_time"));
            return (new CourseGroupListVo()).setCount(courseGroupPage.getTotalElements()).setPage(courseGroupPage
                    .getTotalPages()).setCourseGroups(courseGroupPage.getContent());
        } catch (Exception e) {
            log.warn("???????????????????????????", e);
        }
        return null;
    }

    @Override
    public ResponseResult getCourseGroupByNameForPage(String courseGroupName, int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAllByNameIsLike(NStringUtil
                    .joint("%{}%", courseGroupName), PageRequest.of(page, size));
            return (ResponseResult.enSuccess().add("recordsTotal", courseGroupRepository.count())
                    .add("recordsFiltered", courseGroupPage.getTotalElements())
                    .add("data", courseGroupEntity2Vo(courseGroupPage.getContent())));
        } catch (Exception e) {
            log.warn("???????????????????????????", e);
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getCourseGroupByTeacherAndNameForPage(String teacherId, String courseGroupName, int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAllByNameIsLikeAndTeacher_TeacherIdIsLike(NStringUtil
                    .joint("%{}%", courseGroupName), NStringUtil.joint("%{}%", teacherId), PageRequest.of(page, size));
            return (ResponseResult.enSuccess().add("recordsTotal", courseGroupRepository.count())
                    .add("recordsFiltered", courseGroupPage.getTotalElements())
                    .add("data", courseGroupEntity2Vo(courseGroupPage.getContent())));
        } catch (Exception e) {
            log.warn("???????????????????????????", e);
        }
        return ResponseResult.enFail();
    }

    @Override
    public CourseGroupListVo getCourseGroupListByTeacherAndNameForPage(UserEntity user, String courseGroupName, int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAllByTeacherAndNameIsLike(teacherRepository.findByUser(user)
                    , NStringUtil.joint("%{}%", courseGroupName), PageRequest.of(page, size, Sort.Direction.DESC, "createTime"));
            return (new CourseGroupListVo()).setCount(courseGroupPage.getTotalElements()).setPage(courseGroupPage
                    .getTotalPages()).setCourseGroups(courseGroupPage.getContent());
        } catch (Exception e) {
            log.warn("???????????????????????????", e);
        }
        return null;
    }

    @Override
    public CourseGroupListVo getCourseGroupListByStudentAndNameForPage(UserEntity user, String courseGroupName, int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAllByStudentAndNameIsLike(studentRepository.findByUser(user)
                    , NStringUtil.joint("%{}%", courseGroupName), PageRequest.of(page, size, Sort.Direction.DESC, "create_time"));
            return (new CourseGroupListVo()).setCount(courseGroupPage.getTotalElements()).setPage(courseGroupPage
                    .getTotalPages()).setCourseGroups(courseGroupPage.getContent());
        } catch (Exception e) {
            log.warn("???????????????????????????", e);
        }
        return null;
    }

    @Override
    public ResponseResult getCourseGroupByTeacherOrNameForPage(String teacherId, String courseGroupName, int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAllByNameIsLikeOrTeacher_TeacherIdIsLike(NStringUtil
                    .joint("%{}%", courseGroupName), NStringUtil.joint("%{}%", teacherId), PageRequest.of(page, size));
            return (ResponseResult.enSuccess().add("recordsTotal", courseGroupRepository.count())
                    .add("recordsFiltered", courseGroupPage.getTotalElements())
                    .add("data", courseGroupEntity2Vo(courseGroupPage.getContent())));
        } catch (Exception e) {
            log.warn("???????????????????????????", e);
        }
        return ResponseResult.enFail();
    }

    @Override
    public boolean addCourseGroup(String courseGroupName, String teacherId, String description, Set<String> studentIds) {
        Optional<TeacherEntity> teacherOpt = teacherRepository.findById(teacherId);
        if (!teacherOpt.isPresent()) {
            return false;
        }
        List<StudentEntity> students = studentRepository.findAllById(studentIds);
        if (students.size() != studentIds.size()) {/* ?????????????????????????????? */
            log.info("????????????????????????????????????");
            return false;
        }
        CourseGroupEntity courseGroup = new CourseGroupEntity().setName(courseGroupName).setDescription(description)
                .setTeacher(teacherOpt.get()).setState(true);
        if (!courseGroup.getStudents().addAll(students)) {
            return false;
        }
        return saveCourseGroup(courseGroup);
    }

    @Override
    public boolean addCourseGroupForClass(Integer classId, String courseGroupName, String teacherId, String description) {
        Optional<ClassEntity> classOpt = classRepository.findById(classId);
        if (!classOpt.isPresent()) {
            return false;
        }
        Set<StudentEntity> students = classOpt.get().getStudents();
        if (students.isEmpty()) {/* ????????????????????????????????? */
            return false;
        }
        Optional<TeacherEntity> teacherOpt = teacherRepository.findById(teacherId);
        return teacherOpt.filter(teacherEntity -> {
            CourseGroupEntity courseGroup = new CourseGroupEntity().setName(courseGroupName)
                    .setDescription(description).setTeacher(teacherEntity);
            courseGroup.getStudents().addAll(students);
            return saveCourseGroup(courseGroup);
        }).isPresent();
    }

    @Override
    public boolean saveCourseGroup(CourseGroupEntity courseGroup) {
        try {
            courseGroupRepository.save(courseGroup);
            return true;
        } catch (Exception e) {
            log.error("????????????????????????,msg:{}", e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public boolean updateCourseGroup(Integer courseGroupId, String courseGroupName, String teacherId, String description) {
        Optional<TeacherEntity> teacherOpt = teacherRepository.findById(teacherId);
        if (!teacherOpt.isPresent()) {
            return false;
        }
        Optional<CourseGroupEntity> courseGroupOpt = courseGroupRepository.findById(courseGroupId);
        return courseGroupOpt.filter(courseGroupEntity -> saveCourseGroup(courseGroupEntity.setName(courseGroupName)
                .setTeacher(teacherOpt.get()).setDescription(description))).isPresent();
    }

    @Override
    public boolean deleteCourseGroup(Integer id) {
        try {
            courseGroupRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.warn("???????????????????????????id {}", id);
        }
        return false;
    }

    @Override
    public boolean addStudent2CourseGroup(Integer courseGroupId, Collection<String> studentIds) {
        Optional<CourseGroupEntity> courseGroupOpt = courseGroupRepository.findById(courseGroupId);
        if (!courseGroupOpt.isPresent()) {
            return false;
        }
        List<StudentEntity> students = studentRepository.findAllById(studentIds);
        if (studentIds.size() != students.size()) {
            log.info("???????????????????????????????????????????????? {} ???", courseGroupId);
            return false;
        }
        CourseGroupEntity courseGroup = courseGroupOpt.get();
        if (!courseGroup.getStudents().addAll(students)) {
            return false;
        }
        return saveCourseGroup(courseGroup);
    }

    @Override
    public boolean removeStudentByStudentId(Integer courseGroupId, String studentId) {
        try {
            studentCourseGroupRepository.deleteById(new StudentCourseGroupEntity.Key().setStudentId(studentId)
                    .setCourseGroupId(courseGroupId));
            return true;
        } catch (Exception e) {
            log.warn("?????????{}?????????{}???????????????", studentId, courseGroupId);
        }
        return false;
    }

    /**
     * ???????????????vo??????
     *
     * @param courseGroups ????????????
     * @return CourseGroupVo for list
     */
    @NotNull
    private List<CourseGroupVo> courseGroupEntity2Vo(@NotNull Collection<CourseGroupEntity> courseGroups) {
        List<CourseGroupVo> courseGroupVos = new ArrayList<>();
        courseGroups.forEach(courseGroup -> courseGroupVos.add(courseGroupEntity2Vo(courseGroup)));
        return courseGroupVos;
    }

    /**
     * ???????????????vo??????
     *
     * @param courseGroup ????????????
     * @return CourseGroupVo
     */
    private CourseGroupVo courseGroupEntity2Vo(@NotNull CourseGroupEntity courseGroup) {
        return (new CourseGroupVo().setId(courseGroup.getId())
                .setTeacherId(courseGroup.getTeacher().getTeacherId())
                .setTeacherRealName(courseGroup.getTeacher().getUser().getRealName())
                .setCourseGroupName(courseGroup.getName())
                .setDescription(courseGroup.getDescription())
                .setDatetime(courseGroup.getCreateTime())
                .setCountStudent(studentRepository.countByCourseGroup_Id(courseGroup.getId()))
                .setState(courseGroup.getState()));
    }
}
