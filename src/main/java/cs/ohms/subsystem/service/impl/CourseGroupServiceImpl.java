package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.TeacherEntity;
import cs.ohms.subsystem.repository.CourseGroupRepository;
import cs.ohms.subsystem.service.CourseGroupService;
import cs.ohms.subsystem.service.TeacherService;
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

/**
 * 2020/5/23 23:23
 *
 * @author LRC
 **/
@Service("courseGroupService")
@Slf4j
public class CourseGroupServiceImpl implements CourseGroupService {
    private CourseGroupRepository courseGroupRepository;
    private TeacherService teacherService;

    @Autowired
    public CourseGroupServiceImpl(CourseGroupRepository courseGroupRepository, TeacherService teacherService) {
        this.courseGroupRepository = courseGroupRepository;
        this.teacherService = teacherService;
    }


    @Override
    public List<CourseGroupEntity> findAll() {
        return courseGroupRepository.findAll();
    }

    @Override
    public ResponseResult getCourseGroupByPage (int start, int length) {
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
                    .setState(!courseGroup.getState() ? "已结束" : "正在进行中");
            BeanUtils.copyProperties(courseGroup, courseGroupVo);
            courseGroupVos.add(courseGroupVo);
        });
        long count = courseGroupRepository.count();
        return ResponseResult.enSuccess().add("recordsTotal", count).add("recordsFiltered", count).add("data", courseGroupVos);
    }

    @Override
    public boolean saveCourseGroup(CourseGroupEntity courseGroup) {
        try{
            courseGroupRepository.save(courseGroup);
            return true;
        }catch (Exception e){
            log.error("保存课程信息失败,msg:{}", e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public boolean saveCourseGroup (Integer id, String teacherRealName,String courseGroupName,  String description, String state) {
        TeacherEntity teacher = teacherService.findTeacherByRealName(teacherRealName);
        if(null == teacher){
            return false;
        }
        if(id == null) {
         return saveCourseGroup(new CourseGroupEntity()
                    .setName(courseGroupName)
                    .setTeacher(teacher)
                    .setDescription(description)
                    .setState(state.equals("正在进行中")));
        }
        Optional<CourseGroupEntity> optionalCourseGroup = courseGroupRepository.findById(id);
        return optionalCourseGroup.filter(courseGroupEntity ->
                saveCourseGroup(courseGroupEntity
                        .setName(courseGroupName)
                        .setTeacher(teacher)
                        .setDescription(description)
                        .setState(state.equals("正在进行中")))).isPresent();
    }

    @Override
    public boolean deleteCourseGroup (Integer id) {
        try {
            courseGroupRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.warn("删除课群信息失败，id {}", id);
        }
        return false;
    }
}
