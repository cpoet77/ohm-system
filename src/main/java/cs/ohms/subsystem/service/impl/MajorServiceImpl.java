package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CollegeEntity;
import cs.ohms.subsystem.entity.MajorEntity;
import cs.ohms.subsystem.repository.CollegeRepository;
import cs.ohms.subsystem.repository.MajorRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.service.CollegeService;
import cs.ohms.subsystem.service.MajorService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.viewobject.MajorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
 * 2020/5/23 1:40
 *
 * @author LRC
 */
@Service("majorService")
@Slf4j
public class MajorServiceImpl implements MajorService {
    private MajorRepository majorRepository;
    private StudentRepository studentRepository;
    private CollegeRepository collegeRepository;
    private ResourceService resourceService;
    private CollegeService collegeService;

    @Autowired
    public MajorServiceImpl(MajorRepository majorRepository, StudentRepository studentRepository, CollegeRepository collegeRepository
            , ResourceService resourceService, CollegeService collegeService) {
        this.majorRepository = majorRepository;
        this.studentRepository = studentRepository;
        this.collegeRepository = collegeRepository;
        this.resourceService = resourceService;
        this.collegeService = collegeService;
    }

    @Override
    public List<MajorEntity> findAll() {
        return majorRepository.findAll();
    }

    @Override
    public List<MajorVo> findAllVoByCollege(Integer collegeId) {
        List<MajorEntity> majorEntities = collegeId == null ? majorRepository.findAll() : majorRepository.findAllByCollege_Id(collegeId);
        List<MajorVo> majorVos = new ArrayList<>();
        majorEntities.forEach(majorEntity -> majorVos.add(new MajorVo().setId(majorEntity.getId()).setName(majorEntity.getName())));
        return majorVos;
    }

    @Override
    public ResponseResult getMajorByCollegeAndPage(Integer collegeId, int start, int length) {
        Pageable pageable = PageRequest.of(resourceService.calculatePageNum(start, length), length, Sort.Direction.DESC
                , "datetime");
        Page<MajorEntity> majors = (collegeId == null ? majorRepository.findAll(pageable) : majorRepository.findByCollege_Id(collegeId, pageable));
        List<MajorVo> majorVos = new ArrayList<>();
        majors.forEach(major -> {
            MajorVo majorVo = new MajorVo().setCountStudents(studentRepository.countByMajor_id(major.getId()))
                    .setCollege(major.getCollege().getName()).setCollegeId(major.getCollege().getId());
            BeanUtils.copyProperties(major, majorVo);
            majorVos.add(majorVo);
        });
        long count = majorRepository.count();
        return (ResponseResult.enSuccess().add("recordsTotal", count).add("recordsFiltered", collegeId != null
                ? majorRepository.countByCollege_Id(collegeId) : count).add("data", majorVos));
    }

    @Override
    public boolean saveMajor(MajorEntity major) {
        try {
            majorRepository.save(major);
            return true;
        } catch (Exception e) {
            log.error("保存专业信息失败, msg:{}", e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public boolean saveMajor(Integer majorId, String majorName, Integer collegeId) {
        Optional<CollegeEntity> collegeOptional = collegeRepository.findById(collegeId);
        if (!collegeOptional.isPresent()) {
            return false;
        }
        if (majorId == null) {
            return saveMajor(new MajorEntity().setName(majorName).setCollege(collegeOptional.get()));
        }
        Optional<MajorEntity> majorOptional = majorRepository.findById(majorId);
        return majorOptional.filter(majorEntity -> saveMajor(majorEntity.setName(majorName).setCollege(collegeOptional.get()))).isPresent();
    }

    @Override
    public boolean deleteMajor(Integer majorId) {
        try {
            majorRepository.deleteById(majorId);
            return true;
        } catch (Exception e) {
            log.warn("删除专业信息失败，id {}", majorId);
        }
        return false;
    }

    @Override
    @Cacheable(cacheNames = {"user", "common"}, key = "#identity + '_' + #collegeName + '_' +#majorName")
    public MajorEntity addMajor(String identity, String collegeName, String majorName) {
        if (majorRepository.existsByName(majorName)) {
            return majorRepository.findByName(majorName);
        }
        CollegeEntity college = collegeService.addCollege(identity, collegeName);
        if (college != null) {
            try {
                return majorRepository.save(new MajorEntity().setName(majorName).setCollege(college));
            } catch (Exception e) {
                log.warn("添加专业失败！{}", majorName);
            }
        }
        return null;
    }
}
