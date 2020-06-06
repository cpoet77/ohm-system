package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.ClassEntity;
import cs.ohms.subsystem.entity.MajorEntity;
import cs.ohms.subsystem.repository.ClassRepository;
import cs.ohms.subsystem.repository.MajorRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.service.ClassService;
import cs.ohms.subsystem.service.MajorService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.viewobject.ClassVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private MajorRepository majorRepository;
    private ResourceService resourceService;
    private MajorService majorService;

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository, StudentRepository studentRepository, MajorRepository majorRepository
            , ResourceService resourceService, MajorService majorService) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
        this.resourceService = resourceService;
        this.majorService = majorService;
    }


    @Override
    public ResponseResult getClassByCollegeAndMajorAndPage(Integer collegeId, Integer majorId, Integer start, Integer length) {
        Pageable pageable = PageRequest.of(resourceService.calculatePageNum(start, length), length, Sort.Direction.DESC, "datetime");
        Page<ClassEntity> classEntities = (majorId == -1 ? (collegeId == -1 ? classRepository.findAll(pageable)
                : classRepository.findAllByCollege_Id(collegeId, pageable)) : classRepository.findAllByMajor_Id(majorId, pageable));
        List<ClassVo> classVos = new ArrayList<>();
        classEntities.forEach(classEntity -> classVos.add(new ClassVo()
                .setId(classEntity.getId())
                .setName(classEntity.getName())
                .setCollegeId(classEntity.getMajor().getCollege().getId())
                .setCollegeName(classEntity.getMajor().getCollege().getName())
                .setMajorId(classEntity.getMajor().getId())
                .setMajorName(classEntity.getMajor().getName())
                .setDatetime(classEntity.getDatetime())
                .setCountStudent(studentRepository.countByClazz_id(classEntity.getId()))));
        long count = classRepository.count();
        return (ResponseResult.enSuccess().add("recordsTotal", count).add("recordsFiltered", majorId != -1
                ? classRepository.countByMajor_Id(majorId) : (collegeId != -1 ? classRepository.countByCollege_Id(collegeId)
                : count)).add("data", classVos));
    }

    @Override
    public List<ClassVo> getAllClassByMajor(Integer majorId) {
        List<ClassEntity> classEntities = classRepository.findAllByMajor_Id(majorId);
        List<ClassVo> classVos = new ArrayList<>();
        classEntities.forEach(clazz -> classVos.add(new ClassVo().setId(clazz.getId())
                .setName(clazz.getName())));
        return classVos;
    }

    @Override
    @Cacheable(cacheNames = {"user", "common"}, key = "#identity+ '_' +#collegeName + '_' +#majorName+ '_' +#className")
    public ClassEntity addClass(String identity, String collegeName, String majorName, String className) {
        if (classRepository.existsByName(className)) {
            return classRepository.findByName(className);
        }
        MajorEntity major = majorService.addMajor(identity, collegeName, majorName);
        if (major != null) {
            try {
                return classRepository.save(new ClassEntity().setName(className).setMajor(major));
            } catch (Exception e) {
                log.warn("添加班级失败!{}", className);
            }
        }
        return null;
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
    public boolean saveClass(Integer classId, String className, Integer majorId) {
        Optional<MajorEntity> majorOpt = majorRepository.findById(majorId);
        if (majorOpt.isPresent()) {
            if (classId == null) {
                return saveClass(new ClassEntity().setName(className).setMajor(majorOpt.get()));
            } else {
                Optional<ClassEntity> classOpt = classRepository.findById(classId);
                if (classOpt.isPresent()) {
                    return saveClass(classOpt.get().setName(className).setMajor(majorOpt.get()));
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteClass(Integer classId) {
        try {
            classRepository.deleteById(classId);
            return true;
        } catch (Exception e) {
            log.warn("班级信息删除失败, classId : {}, msg : {}", classId, e.getLocalizedMessage());
        }
        return false;
    }
}
