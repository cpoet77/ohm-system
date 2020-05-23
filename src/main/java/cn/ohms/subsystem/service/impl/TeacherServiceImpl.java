// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cn.ohms.subsystem.service.impl;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.component.PasswordCMP;
import cn.ohms.subsystem.entity.RoleEntity;
import cn.ohms.subsystem.entity.TeacherEntity;
import cn.ohms.subsystem.entity.UserEntity;
import cn.ohms.subsystem.repository.RoleRepository;
import cn.ohms.subsystem.repository.TeacherRepository;
import cn.ohms.subsystem.service.ResourceService;
import cn.ohms.subsystem.service.TeacherService;
import cn.ohms.subsystem.service.UserService;
import cn.ohms.subsystem.tableobject.TeacherTo;
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
@Service("teacherService")
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private UserService userService;
    private ResourceService resourceService;
    private TeacherRepository teacherRepository;
    private RoleRepository roleRepository;
    private PasswordCMP passwordCMP;

    @Autowired
    public TeacherServiceImpl(UserService userService, ResourceService resourceService, TeacherRepository teacherRepository
            , RoleRepository roleRepository, PasswordCMP passwordCMP) {
        this.userService = userService;
        this.resourceService = resourceService;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.passwordCMP = passwordCMP;
    }

    @Override
    public List<TeacherEntity> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public ResponseResult importTeacherInfo(InputStream in) {
        RoleEntity role = roleRepository.findByName(UserService.USER_TEACHER_ROLE);
        if (role == null) {
            log.error("请确保教师角色的存在！");
            return ResponseResult.enError();
        }
        try {
            List<TeacherTo> errList = new ArrayList<>();
            List<TeacherTo> teacherTos = resourceService.inputStreamToTable(TeacherTo.class, in);
            teacherTos.forEach(teacherTo -> {
                String salt = passwordCMP.produceSalt();
                UserEntity user = new UserEntity().setName(userService.createDefaultName(teacherTo.getTeacherId()))
                        .setRealName(teacherTo.getRealName()).setPassword(passwordCMP.encryptPassword(teacherTo.getPassword(), salt))
                        .setSalt(salt).setSex("男".equals(teacherTo.getSex()) ? 'M' : 'F');
                user.getRoles().add(role);
                if (userService.saveUser(user)) {
                    TeacherEntity teacher = new TeacherEntity().setTeacherId(teacherTo.getTeacherId()).setUser(user);
                    if (!saveTeacher(teacher)) {
                        errList.add(teacherTo);
                    }
                } else {
                    errList.add(teacherTo);
                }
            });
            int count = teacherTos.size();
            int fail = errList.size();
            int success = count - fail;
            return (ResponseResult.enSuccess().add("count", count).add("success", success).add("fail", fail)
                    .add("errList", errList));
        } catch (Exception e) {
            log.warn("导入表格失败！", e);
        }
        return ResponseResult.enError();
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
