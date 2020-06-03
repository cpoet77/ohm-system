package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.StudentEntity;
import cs.ohms.subsystem.entity.TeacherEntity;
import cs.ohms.subsystem.entity.middle.StudentCourseGroupEntity;
import cs.ohms.subsystem.repository.CourseGroupRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.repository.TeacherRepository;
import cs.ohms.subsystem.repository.middle.StudentCourseGroupRepository;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.viewobject.CourseGroupVo;
import lombok.extern.slf4j.Slf4j;
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
    private ResourceService resourceService;

    @Autowired
    public CourseGroupServiceImpl(CourseGroupRepository courseGroupRepository, TeacherRepository teacherRepository
            , StudentRepository studentRepository, StudentCourseGroupRepository studentCourseGroupRepository
            , ResourceService resourceService) {
        this.courseGroupRepository = courseGroupRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.studentCourseGroupRepository = studentCourseGroupRepository;
        this.resourceService = resourceService;
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
    public ResponseResult getCourseGroupByPage(int start, int length) {
        Page<CourseGroupEntity> courseGroups = courseGroupRepository
                .findAll(PageRequest.of(resourceService.calculatePageNum(start, length), length, Sort.Direction.ASC
                        , "createTime"));
        List<CourseGroupVo> courseGroupVos = new ArrayList<>();
        courseGroups.forEach(courseGroup -> courseGroupVos.add(new CourseGroupVo()
                .setTeacherRealName(courseGroup.getTeacher().getUser().getRealName())
                .setCourseGroupName(courseGroup.getName())
                .setDatetime(courseGroup.getCreateTime())
                .setId(courseGroup.getId())
                .setTeacherId(courseGroup.getTeacher().getUser().getId())
                .setState(courseGroup.getState())
                .setCountStudent(courseGroup.getStudents().size())));
        long count = courseGroupRepository.count();
        return ResponseResult.enSuccess().add("recordsTotal", count).add("recordsFiltered", count).add("data", courseGroupVos);
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
}
