package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.ClassEntity;
import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.StudentEntity;
import cs.ohms.subsystem.entity.TeacherEntity;
import cs.ohms.subsystem.entity.middle.StudentCourseGroupEntity;
import cs.ohms.subsystem.repository.ClassRepository;
import cs.ohms.subsystem.repository.CourseGroupRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.repository.TeacherRepository;
import cs.ohms.subsystem.repository.middle.StudentCourseGroupRepository;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.viewobject.CourseGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseResult getCourseGroupForPage(int page, int size) {
        try {
            Page<CourseGroupEntity> courseGroupPage = courseGroupRepository.findAll(PageRequest.of(page, size));
            return (ResponseResult.enSuccess().add("recordsTotal", courseGroupRepository.count())
                    .add("recordsFiltered", courseGroupPage.getTotalElements())
                    .add("data", courseGroupEntity2Vo(courseGroupPage.getContent())));
        } catch (Exception e) {
            log.warn("课群信息查询失败！");
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
            log.warn("课群信息查询失败！");
        }
        return ResponseResult.enFail();
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
            log.warn("课群信息查询失败！");
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
            log.warn("课群信息查询失败！");
        }
        return ResponseResult.enFail();
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
            log.warn("课群信息查询失败！");
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
        if (students.size() != studentIds.size()) {/* 并非所有的学生都存在 */
            log.info("添加课群出现未定义的学生");
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
        if (students.isEmpty()) {/* 这个班一个学生也没有哦 */
            return false;
        }
        Optional<TeacherEntity> teacherOpt = teacherRepository.findById(teacherId);
        return teacherOpt.filter(teacherEntity -> saveCourseGroup(new CourseGroupEntity().setName(courseGroupName)
                .setDescription(description).setTeacher(teacherEntity).setStudents(students))).isPresent();
    }

    @Override
    public boolean saveCourseGroup(CourseGroupEntity courseGroup) {
        try {
            courseGroupRepository.save(courseGroup);
            return true;
        } catch (Exception e) {
            log.error("保存课程信息失败,msg:{}", e.getLocalizedMessage());
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
            log.warn("删除课群信息失败，id {}", id);
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
            log.info("添加课群出现未经定义的学生到课群 {} 中", courseGroupId);
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
            log.warn("将学生{}从课群{}移除失败！", studentId, courseGroupId);
        }
        return false;
    }

    private List<CourseGroupVo> courseGroupEntity2Vo(Collection<CourseGroupEntity> courseGroups) {
        List<CourseGroupVo> courseGroupVos = new ArrayList<>();
        courseGroups.forEach(courseGroup -> courseGroupVos.add(new CourseGroupVo().setId(courseGroup.getId())
                .setTeacherId(courseGroup.getTeacher().getTeacherId())
                .setTeacherRealName(courseGroup.getTeacher().getUser().getRealName())
                .setCourseGroupName(courseGroup.getName())
                .setDescription(courseGroup.getDescription())
                .setDatetime(courseGroup.getCreateTime())
                .setCountStudent(studentRepository.countByCourseGroup_Id(courseGroup.getId()))
                .setState(courseGroup.getState())));
        return courseGroupVos;
    }
}
