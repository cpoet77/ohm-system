// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/8.
package cs.ohms.subsystem.service.impl;

import cn.nsleaf.utils.NSimpleHttpException;
import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.LoginRecordEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.repository.LoginRecordRepository;
import cs.ohms.subsystem.service.LoginService;
import cs.ohms.subsystem.utils.JsonUtil;
import cs.ohms.subsystem.utils.NSIPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * LoginService实现
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see LoginService
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
    public void loginRecord(HttpServletRequest request, UserEntity user, String userAgent) {
        // 获取登录ip地址
        String ip = NSIPUtil.getIpAddress(request);
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
                loginRecord.setAgent(userAgent);
                loginRecordRepository.save(loginRecord);
            }
        } catch (NSimpleHttpException e) {
            log.info("Login record, failed to get ip address information.ip {} : , message : {}", ip, e.getMessage());
        }
    }

    @Override
    public ResponseResult getLoginInfoListByPage(UserEntity user, Integer page, Integer size) {
        try {
            Page<LoginRecordEntity> userPage = loginRecordRepository.findByUser(user, PageRequest.of(page, size, Sort.Direction.DESC, "datetime"));
            return ResponseResult.enSuccess().add("list", userPage.toList()).add("count", userPage.getTotalElements()).add("size", userPage.getNumberOfElements()).add("page", page);
        } catch (Exception e) {
            log.warn("获取登录日志失败，userId : {}", user.getId());
        }
        return ResponseResult.enFail();
    }
}
