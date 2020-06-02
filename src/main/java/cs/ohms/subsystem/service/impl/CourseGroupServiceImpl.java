package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.StudentEntity;
import cs.ohms.subsystem.entity.TeacherEntity;
import cs.ohms.subsystem.repository.CourseGroupRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.repository.TeacherRepository;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.viewobject.CourseGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    public CourseGroupServiceImpl(CourseGroupRepository courseGroupRepository, TeacherRepository teacherRepository
            , StudentRepository studentRepository) {
        this.courseGroupRepository = courseGroupRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public List<CourseGroupEntity> findAll() {
        return courseGroupRepository.findAll();
    }

    @Override
    public ResponseResult getCourseGroupByPage(int start, int length) {
        int page = (int) Math.ceil((double) start / length);
        Page<CourseGroupEntity> courseGroups = courseGroupRepository
                .findAll(PageRequest.of(page, length, Sort.Direction.ASC, "createTime"));
        List<CourseGroupVo> courseGroupVos = new ArrayList<>();
        courseGroups.forEach(courseGroup -> {
            CourseGroupVo courseGroupVo = new CourseGroupVo()
                    .setTeacherRealName(courseGroup.getTeacher().getUser().getRealName())
                    .setCourseGroupName(courseGroup.getName())
                    .setDatetime(courseGroup.getCreateTime())
                    .setDescription(courseGroup.getDescription())
                    .setId(courseGroup.getId())
                    .setTeacherId(courseGroup.getTeacher().getUser().getId())
                    .setState(courseGroup.getState());
            BeanUtils.copyProperties(courseGroup, courseGroupVo);
            courseGroupVos.add(courseGroupVo);
        });
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
}
