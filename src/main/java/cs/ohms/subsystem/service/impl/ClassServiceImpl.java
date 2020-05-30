package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.entity.ClassEntity;
import cs.ohms.subsystem.repository.ClassRepository;
import cs.ohms.subsystem.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 2020/5/30 14:29
 *
 * @author _Struggler
 */
@Service("classService")
@Slf4j
public class ClassServiceImpl implements ClassService {
    private ClassRepository classRepository;

    @Autowired
    public ClassServiceImpl (ClassRepository classRepository) {
        this.classRepository = classRepository;
    }


    @Override
    @Cacheable(cacheNames = {"common"}, key = "#name")
    public ClassEntity findClassHashCacheByName (String name) {
        return classRepository.findByName(name);
    }
}
