// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/25.
package cn.ohms.subsystem.service.impl;

import cn.ohms.subsystem.entity.UserEntity;
import cn.ohms.subsystem.repository.UserRepository;
import cn.ohms.subsystem.service.AppService;
import cn.ohms.subsystem.service.UserService;
import cn.ohms.subsystem.utils.NStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * UserService实现
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    private AppService appService;
    private UserRepository userRepository;
    private static String USER_NAME_PREFIX = "";

    @Autowired
    public UserServiceImpl(AppService appService, UserRepository userRepository) {
        this.appService = appService;
        this.userRepository = userRepository;
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
}
