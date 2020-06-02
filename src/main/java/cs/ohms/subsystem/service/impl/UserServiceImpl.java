// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/25.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.entity.RoleEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.repository.RoleRepository;
import cs.ohms.subsystem.repository.UserRepository;
import cs.ohms.subsystem.service.AppService;
import cs.ohms.subsystem.service.UserService;
import cs.ohms.subsystem.utils.NStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * UserService实现
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    /**
     * 生成用户名前缀
     */
    private static String USER_NAME_PREFIX = "";
    private AppService appService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(AppService appService, UserRepository userRepository, RoleRepository roleRepository) {
        this.appService = appService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        USER_NAME_PREFIX = appService.getAsString("ohms.user.id.prefix");
    }

    @Override
    public UserEntity findUserByName(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public String createDefaultName(String name) {
        if (NStringUtil.isEmpty(USER_NAME_PREFIX)) {
            return name;
        }
        return (NStringUtil.joint("{}-{}", USER_NAME_PREFIX, name));
    }

    @Override
    public boolean saveUser(UserEntity user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.warn("保存用户信息失败, msg : {}", e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public UserEntity findUserByRealName(String realName) {
        return userRepository.findByRealName(realName);
    }

    @Override
    public boolean deleteUserById(Boolean currentUserIsAdmin, Integer userId) {
        try {
            RoleEntity adminRole = roleRepository.findByName(UserService.USER_ADMIN_ROLE);
            RoleEntity teachingSecretary = roleRepository.findByName(UserService.USER_TEACHING_SECRETARY_ROLE);
            assert adminRole != null && teachingSecretary != null;
            Optional<UserEntity> userOpt = userRepository.findById(userId);
            if (!userOpt.isPresent()) {
                return false;
            }
            UserEntity user = userOpt.get();
            // 如果准备删除的用户是超级管理员或者一个教学秘书正在删除另外一个教学秘书的信息，那么拒绝操作。
            if (user.getRoles().contains(adminRole) || (!currentUserIsAdmin && user.getRoles().contains(teachingSecretary))) {
                return false;/* 不允许删除 */
            }
            userRepository.delete(user);
            return true;
        } catch (Exception e) {
            log.warn("用户删除失败！, userId {} , msg: {}", userId, e.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public boolean updateUserById(Boolean currentUserIsAdmin, Integer userId, String realName, Character sex, String email, String phone) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }
        RoleEntity adminRole = roleRepository.findByName(UserService.USER_ADMIN_ROLE);
        RoleEntity teachingSecretary = roleRepository.findByName(UserService.USER_TEACHING_SECRETARY_ROLE);
        assert adminRole != null && teachingSecretary != null;
        UserEntity user = userOpt.get();
        // 判断操作的权限，教学秘书只能操作非超管且不是教学秘书的账号
        if (!currentUserIsAdmin && (user.getRoles().contains(adminRole) || user.getRoles().contains(teachingSecretary))) {
            return false;
        }
        return saveUser(user.setRealName(realName).setSex(sex).setPhone(phone).setEmail(email));
    }

    @Override
    public boolean saveSkin(UserEntity user, String skinName) {
        try {
            user.setSkin(skinName);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.warn("用户主题设置失败！");
        }
        return false;
    }
}
