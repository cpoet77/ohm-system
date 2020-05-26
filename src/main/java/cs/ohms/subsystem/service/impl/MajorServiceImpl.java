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
import cs.ohms.subsystem.tableobject.MajorTo;
import cs.ohms.subsystem.viewobject.MajorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 2020/5/23 1:40
 *
 * @author LRC
 **/
@Service("majorService")
@Slf4j
public class MajorServiceImpl implements MajorService {
    private CollegeService collegeService;
    private ResourceService resourceService;
    private MajorRepository majorRepository;
    private StudentRepository studentRepository;
    private CollegeRepository collegeRepository;

    @Autowired
    public MajorServiceImpl(CollegeService collegeService, ResourceService resourceService, MajorRepository majorRepository
            , StudentRepository studentRepository, CollegeRepository collegeRepository) {
        this.collegeService = collegeService;
        this.resourceService = resourceService;
        this.majorRepository = majorRepository;
        this.studentRepository = studentRepository;
        this.collegeRepository = collegeRepository;
    }

    @Override
    public List<MajorEntity> findAll() {
        return majorRepository.findAll();
    }

    @Override
    public ResponseResult getMajorByPage(int start, int length) {
        int page = (int) Math.ceil((double) start / length);
        Page<MajorEntity> majors = majorRepository.findAll(PageRequest.of(page, length, Sort.Direction.DESC, "datetime"));
        List<MajorVo> majorVos = new ArrayList<>();
        majors.forEach(major -> {
            MajorVo majorVo = new MajorVo().setCountStudents(studentRepository.countByMajor(major))
                    .setCollege(major.getCollege().getName()).setCollegeId(major.getCollege().getId());
            BeanUtils.copyProperties(major, majorVo);
            majorVos.add(majorVo);
        });
        long count = majorRepository.count();
        return ResponseResult.enSuccess().add("recordsTotal", count).add("recordsFiltered", count).add("data", majorVos);
    }

    @Override
    public ResponseResult importMajorInfo(InputStream in) {
        List<MajorTo> errorList = new ArrayList<>();
        try {
            List<MajorTo> majorTos = resourceService.inputStreamToTable(MajorTo.class, in);
            majorTos.forEach(majorTo -> {
                CollegeEntity college = collegeService.findCollegeHasCacheByName(majorTo.getCollege());
                if (college != null) {
                    MajorEntity major = new MajorEntity().setName(majorTo.getName()).setCollege(college);
                    if (!saveMajor(major)) {
                        errorList.add(majorTo);
                    }
                } else {
                    errorList.add(majorTo);
                }
            });
            int count = majorTos.size();
            int fail = errorList.size();
            int success = count - fail;
            return (ResponseResult.enSuccess().add("count", count).add("success", success).add("fail", fail)
                    .add("errList", errorList));
        } catch (
                Exception e) {
            log.warn("导入表格失败！", e);
        }
        return ResponseResult.enError();
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
    public MajorEntity findMajorHashCacheByName(String name) {
        return majorRepository.findByName(name);
    }
}
