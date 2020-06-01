package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.ClassEntity;
import cs.ohms.subsystem.entity.MajorEntity;
import cs.ohms.subsystem.repository.ClassRepository;
import cs.ohms.subsystem.repository.MajorRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.service.ClassService;
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

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository, StudentRepository studentRepository, MajorRepository majorRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
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

    @Override
    @Cacheable(cacheNames = {"common"}, key = "#name")
    public ClassEntity findClassHashCacheByName(String name) {
        return classRepository.findByName(name);
    }
}
