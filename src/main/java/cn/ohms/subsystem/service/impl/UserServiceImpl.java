// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/25.
package cn.ohms.subsystem.service.impl;

import cn.ohms.subsystem.entity.UserEntity;
import cn.ohms.subsystem.repository.UserRepository;
import cn.ohms.subsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService实现
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findUserByName(String username) {
        return userRepository.findByName(username);
    }
}
