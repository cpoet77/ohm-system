// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/8.
package cn.ohms.subsystem.service.impl;

import cn.nsleaf.utils.NSimpleHttpException;
import cn.ohms.subsystem.entity.LoginRecordEntity;
import cn.ohms.subsystem.entity.UserEntity;
import cn.ohms.subsystem.repository.LoginRecordRepository;
import cn.ohms.subsystem.service.LoginService;
import cn.ohms.subsystem.utils.JsonUtil;
import cn.ohms.subsystem.utils.NSIPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * LoginService实现
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see cn.ohms.subsystem.service.LoginService
 */
@Service("loginService")
@Slf4j
public class LoginServiceImpl implements LoginService {

    private LoginRecordRepository loginRecordRepository;

    @Autowired
    public LoginServiceImpl(LoginRecordRepository loginRecordRepository) {
        this.loginRecordRepository = loginRecordRepository;
    }

    @Override
    public void loginRecord(HttpServletRequest request, UserEntity user) {
        // 获取登录ip地址
        String ip = NSIPUtil.getIpAddress(request);
        for(int i = 0;i < 100;++i){
            System.out.println(i);
        }
        try {
            String message = NSIPUtil.get(ip);
            Map<String, String> map = JsonUtil.decode(message, JsonUtil.mapTypeReferenceStr());
            if (map != null) {
                LoginRecordEntity loginRecord = new LoginRecordEntity();
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
