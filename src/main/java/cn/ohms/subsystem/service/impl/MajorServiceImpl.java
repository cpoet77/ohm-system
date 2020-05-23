package cn.ohms.subsystem.service.impl;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.entity.CollegeEntity;
import cn.ohms.subsystem.entity.MajorEntity;
import cn.ohms.subsystem.repository.MajorRepository;
import cn.ohms.subsystem.service.CollegeService;
import cn.ohms.subsystem.service.MajorService;
import cn.ohms.subsystem.service.ResourceService;
import cn.ohms.subsystem.tableobject.MajorTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    public MajorServiceImpl(CollegeService collegeService, ResourceService resourceService, MajorRepository majorRepository) {
        this.collegeService = collegeService;
        this.resourceService = resourceService;
        this.majorRepository = majorRepository;
    }

    @Override
    public List<MajorEntity> findAll() {
        return majorRepository.findAll();
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


}
