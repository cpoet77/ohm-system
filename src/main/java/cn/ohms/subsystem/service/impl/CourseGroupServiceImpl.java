package cn.ohms.subsystem.service.impl;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.entity.CourseGroupEntity;
import cn.ohms.subsystem.entity.TeacherEntity;
import cn.ohms.subsystem.repository.CourseGroupRepository;
import cn.ohms.subsystem.service.CourseGroupService;
import cn.ohms.subsystem.service.ResourceService;
import cn.ohms.subsystem.service.TeacherService;
import cn.ohms.subsystem.tableobject.CourseGroupTo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    private ResourceService resourceService;

    @Autowired
    public CourseGroupServiceImpl(CourseGroupRepository courseGroupRepository, TeacherService teacherService,
                                  ResourceService resourceService) {
        this.courseGroupRepository = courseGroupRepository;
        this.teacherService = teacherService;
        this.resourceService = resourceService;
    }


    @Override
    public List<CourseGroupEntity> findAll() {
        return courseGroupRepository.findAll();
    }

    @Override
    public ResponseResult importCourseGroupInfo(InputStream in) {
        List<CourseGroupTo> errorList = new ArrayList<>();
        try{
            List<CourseGroupTo> courseGroupTos = resourceService.inputStreamToTable(CourseGroupTo.class, in);
            courseGroupTos.forEach(courseGroupTo -> {
                TeacherEntity teacher = teacherService.findTeacherHasCacheByName(courseGroupTo.getTeacher());
                if(teacher != null){
                    CourseGroupEntity courseGroup = new CourseGroupEntity().setName(courseGroupTo.getName()).
                            setTeacher(teacher).setDescription(courseGroupTo.getDescription());
                    if(!saveCourseGroup(courseGroup)){
                        errorList.add(courseGroupTo);
                    }
                }else {
                    errorList.add(courseGroupTo);
                }
            });
            int count = courseGroupTos.size();
            int fail = errorList.size();
            int success = count - fail;
            return (ResponseResult.enSuccess().add("count", count).add("success",success).add("fail", fail)
                .add("errList", errorList));
        } catch (Exception e){
            log.warn("导入表格失败！", e);
        }
        return ResponseResult.enError();
    }

    @Override
    public boolean saveCourseGroup(CourseGroupEntity courseGroup) {
        try{
            courseGroupRepository.save(courseGroup);
            return true;
        }catch (Exception e){
            log.error("保存专业信息失败,msg:{}", e.getLocalizedMessage());
        }
        return false;
    }
}
