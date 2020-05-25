// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CollegeEntity;
import cs.ohms.subsystem.repository.CollegeRepository;
import cs.ohms.subsystem.service.CollegeService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.tableobject.CollegeTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("collegeService")
@Slf4j
public class CollegeServiceImpl implements CollegeService {
    private ResourceService resourceService;
    private CollegeRepository collegeRepository;

    @Autowired
    public CollegeServiceImpl(ResourceService resourceService, CollegeRepository collegeRepository) {
        this.resourceService = resourceService;
        this.collegeRepository = collegeRepository;
    }

    @Override
    public List<CollegeEntity> findAll() {
        return collegeRepository.findAll();
    }

    @Override
    public ResponseResult importCollegeInfo(InputStream in) {
        List<CollegeTo> errorList = new ArrayList<>();
        try {
            List<CollegeTo> collegeTos = resourceService.inputStreamToTable(CollegeTo.class, in);
            collegeTos.forEach(collegeTo -> {
                CollegeEntity college = new CollegeEntity().setName(collegeTo.getName()).setDescription(collegeTo.getDescription());
                if (!saveCollege(college)) {
                    errorList.add(collegeTo);
                }
            });
            int count = collegeTos.size();
            int fail = errorList.size();
            int success = count - fail;
            return (ResponseResult.enSuccess().add("count", count).add("success", success).add("fail", fail)
                    .add("errList", errorList));
        } catch (Exception e) {
            log.warn("导入表格失败！", e);
        }
        return ResponseResult.enError();
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
    @Cacheable(cacheNames = {"common"}, key = "#name")
    public CollegeEntity findCollegeHasCacheByName(String name) {
        return collegeRepository.findByName(name);
    }
}
