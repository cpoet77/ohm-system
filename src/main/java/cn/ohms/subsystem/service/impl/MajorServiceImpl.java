package cn.ohms.subsystem.service.impl;

import cn.ohms.subsystem.entity.MajorEntity;
import cn.ohms.subsystem.repository.MajorRepository;
import cn.ohms.subsystem.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 2020/5/23 1:40
 *
 * @author LRC
 **/
@Service("majorService")
public class MajorServiceImpl implements MajorService {
    private MajorRepository majorRepository;

    @Autowired
    public MajorServiceImpl(MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }


    @Override
    public List<MajorEntity> findAll() {
        return majorRepository.findAll();
    }
}
