// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.entity.TeacherEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.repository.TeacherRepository;
import cs.ohms.subsystem.service.TeacherService;
import cs.ohms.subsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("teacherService")
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private UserService userService;
    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(UserService userService, TeacherRepository teacherRepository) {
        this.userService = userService;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherEntity> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public TeacherEntity findTeacherByRealName (String realName) {
        UserEntity user = userService.findUserByRealName(realName);
        if(user == null){
            return null;
        }
        return user.getTeacher();
    }


    @Override
    public boolean saveTeacher(TeacherEntity teacher) {
        try {
            teacherRepository.save(teacher);
            return true;
        } catch (Exception e) {
            log.error("保存教师信息失败, msg : {}", e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    @Cacheable(cacheNames = {"common"}, key = "#name")
    public TeacherEntity findTeacherHasCacheByName(String name) {
        UserEntity user = userService.findUserByRealName(name);
        if(user == null){
            return null;
        }
        return user.getTeacher();
    }
}
