package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.ClassEntity;
import cs.ohms.subsystem.entity.MajorEntity;
import cs.ohms.subsystem.repository.ClassRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.service.ClassService;
import cs.ohms.subsystem.service.MajorService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.tableobject.ClassTo;
import cs.ohms.subsystem.viewobject.ClassVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 2020/5/30 14:29
 *
 * @author _Struggler
 */
@Service("classService")
@Slf4j
public class ClassServiceImpl implements ClassService {
    private ClassRepository classRepository;
    private StudentRepository studentRepository;
    private ResourceService resourceService;
    private MajorService majorService;

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository, StudentRepository studentRepository, ResourceService resourceService, MajorService majorService) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.resourceService = resourceService;
        this.majorService = majorService;
    }


    @Override
    public ResponseResult getClassByMajorAndPage(Integer majorId, Integer start, Integer length) {
        int page = (int) Math.ceil((double) start / length);
        Pageable pageable = PageRequest.of(page, length, Sort.Direction.DESC, "datetime");
        Page<ClassEntity> classEntities = majorId == null ? classRepository.findAll(pageable) : classRepository.findAllByMajor_Id(majorId, pageable);
        List<ClassVo> classVos = new ArrayList<>();
        classEntities.forEach(classEntity -> {
            classVos.add(new ClassVo()
                    .setId(classEntity.getId())
                    .setName(classEntity.getName())
                    .setCollegeId(classEntity.getMajor().getCollege().getId())
                    .setCollegeName(classEntity.getMajor().getCollege().getName())
                    .setMajorId(classEntity.getMajor().getId())
                    .setMajorName(classEntity.getMajor().getName())
                    .setDatetime(classEntity.getDatetime())
                    .setCountStudent(studentRepository.countByClazz_id(classEntity.getId())));
        });
        long count = classRepository.count();
        return (ResponseResult.enSuccess().add("recordsTotal", count).add("recordsFiltered", majorId != null
                ? classRepository.countByMajor_Id(majorId) : count).add("data", classVos));
    }

    @Override
    public ResponseResult importMajorInfo(InputStream in) {
        List<ClassTo> errList = new ArrayList<>();
        try {
            List<ClassTo> classTos = resourceService.inputStreamToTable(ClassTo.class, in);
            classTos.forEach(classTo -> {
                MajorEntity major = majorService.findMajorHashCacheByName(classTo.getMajorName());
                if (major != null) {
                    ClassEntity clazz = new ClassEntity().setName(classTo.getName()).setMajor(major);
                    if (!saveClass(clazz)) {
                        errList.add(classTo);
                    }
                } else {
                    errList.add(classTo);
                }
            });
            int count = classTos.size();
            int fail = errList.size();
            int success = count - fail;
            return (ResponseResult.enSuccess().add("count", count).add("success", success).add("fail", fail)
                    .add("errList", errList));
        } catch (Exception e) {
            log.warn("导入表格失败！", e);
        }
        return ResponseResult.enError();
    }

    @Override
    public boolean saveClass(ClassEntity classEntity) {
        try {
            classRepository.save(classEntity);
            return true;
        } catch (Exception e) {
            log.warn("班级信息保存失败！msg : {}", e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    @Cacheable(cacheNames = {"common"}, key = "#name")
    public ClassEntity findClassHashCacheByName(String name) {
        return classRepository.findByName(name);
    }
}
