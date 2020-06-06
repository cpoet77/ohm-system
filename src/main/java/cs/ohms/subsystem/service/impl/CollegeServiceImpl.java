// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CollegeEntity;
import cs.ohms.subsystem.repository.CollegeRepository;
import cs.ohms.subsystem.repository.MajorRepository;
import cs.ohms.subsystem.repository.StudentRepository;
import cs.ohms.subsystem.service.CollegeService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.viewobject.CollegeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("collegeService")
@Slf4j
public class CollegeServiceImpl implements CollegeService {
    private CollegeRepository collegeRepository;
    private MajorRepository majorRepository;
    private StudentRepository studentRepository;
    private ResourceService resourceService;

    @Autowired
    public CollegeServiceImpl(CollegeRepository collegeRepository, MajorRepository majorRepository
            , StudentRepository studentRepository, ResourceService resourceService) {
        this.collegeRepository = collegeRepository;
        this.majorRepository = majorRepository;
        this.studentRepository = studentRepository;
        this.resourceService = resourceService;
    }

    @Override
    public List<CollegeVo> findAll() {
        List<CollegeEntity> colleges = collegeRepository.findAll();
        List<CollegeVo> collegeVos = new ArrayList<>();
        colleges.forEach(college -> {
            collegeVos.add(new CollegeVo().setId(college.getId()).setName(college.getName()));
        });
        return collegeVos;
    }

    @Override
    public ResponseResult getCollegesByPage(int start, int length) {
        Page<CollegeEntity> colleges = collegeRepository.findAll(PageRequest.of(resourceService.calculatePageNum(start, length)
                , length, Sort.Direction.DESC, "datetime"));
        long count = collegeRepository.count();
        List<Object> resultList = new ArrayList<>();
        colleges.forEach(college -> {
            CollegeVo collegeVo = new CollegeVo().setCountMajor(majorRepository.countByCollege_Id(college.getId()))
                    .setCountStudent(studentRepository.countByCollege_id(college.getId()));
            BeanUtils.copyProperties(college, collegeVo);
            resultList.add(collegeVo);
        });
        return ResponseResult.enSuccess().add("recordsTotal", count).add("recordsFiltered", count).add("data", resultList);
    }

    @Override
    public boolean saveCollege(CollegeEntity college) {
        try {
            collegeRepository.save(college);
            return true;
        } catch (Exception e) {
            log.warn("保存数据失败! msg : {}", e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public boolean saveCollege(Integer id, String name, String description) {
        if (id == null) {
            return saveCollege(new CollegeEntity().setName(name).setDescription(description));
        }
        Optional<CollegeEntity> collegeOptional = collegeRepository.findById(id);
        return collegeOptional.filter(collegeEntity -> saveCollege(collegeEntity.setName(name).setDescription(description))).isPresent();
    }

    @Override
    public boolean deleteCollege(Integer id) {
        try {
            collegeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.warn("删除学院信息失败！id {}", id);
        }
        return false;
    }

    @Override
    @Cacheable(cacheNames = {"user", "common"}, key = "#identity + '_' + #name")
    public CollegeEntity addCollege(String identity, String name) {
        if (collegeRepository.existsByName(name)) {
            return collegeRepository.findByName(name);
        }
        if (identity.equals("admin")) {
            try {
                return collegeRepository.save(new CollegeEntity().setName(name));
            } catch (Exception e) {
                log.warn("创建学院失败！name : {}", name);
            }
        }
        return null;
    }
}
