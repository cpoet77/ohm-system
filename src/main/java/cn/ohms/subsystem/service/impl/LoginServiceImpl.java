// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/8.
package cn.ohms.subsystem.service.impl;

import cn.nsleaf.utils.NSimpleHttpException;
import cs.ohmsubsystem.entity.LoginRecordEntity;
import cs.ohmsubsystem.entity.UserEntity;
import cs.ohmsubsystem.repository.LoginRecordRepository;
import cs.ohmsubsystem.service.LoginService;
import cs.ohmsubsystem.utils.JsonUtil;
import cs.ohmsubsystem.utils.NSIPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * LoginService实现
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see cs.ohmsubsystem.service.LoginService
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private final static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private LoginRecordRepository loginRecordRepository;

    @Autowired
    public LoginServiceImpl(LoginRecordRepository loginRecordRepository) {
        this.loginRecordRepository = loginRecordRepository;
    }

    @Override
    public void loginRecord(HttpServletRequest request, Integer userId) {
        // 获取登录ip地址
        String ip = NSIPUtil.getIpAddress(request);
        try {
            String message = NSIPUtil.get(ip);
            Map<String, String> map = JsonUtil.decode(message, JsonUtil.mapTypeReferenceStr());
            if (map != null) {
                LoginRecordEntity loginRecord = new LoginRecordEntity();
                UserEntity user = new UserEntity();
                user.setId(userId);
                loginRecord.setUser(user);
                loginRecord.setLoginIp(ip);
                loginRecord.setProvince(map.get("pro"));
                loginRecord.setProvinceCode(map.get("proCode"));
                loginRecord.setCity(map.get("city"));
                loginRecord.setCityCode(map.get("cityCode"));
                loginRecord.setAddress(map.get("addr"));
                loginRecord.setAddress(request.getHeader("UserEntity-Agent"));
                loginRecordRepository.save(loginRecord);
            }
        } catch (NSimpleHttpException e) {
            log.info("Login record, failed to get ip address information.ip {} : , message : {}", ip, e.getMessage());
        }
    }
}
