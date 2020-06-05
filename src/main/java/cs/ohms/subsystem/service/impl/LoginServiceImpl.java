// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/8.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.LoginRecordEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.repository.LoginRecordRepository;
import cs.ohms.subsystem.service.LoginService;
import cs.ohms.subsystem.utils.JsonUtil;
import cs.ohms.subsystem.utils.NSIPUtil;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.viewobject.LoginRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        } catch (Exception e) {
            log.info("Login record, failed to get ip address information.ip {} : , message : {}", ip, e.getMessage());
        }
    }

    @Override
    public ResponseResult getLoginRecordListByUserNameIsLikeFoPage(String userName, int page, int size) {
        try {
            Page<LoginRecordEntity> loginRecordPage = loginRecordRepository.findByUser_NameIsLike(NStringUtil
                    .joint("%{}%", userName), PageRequest.of(page, size, Sort.Direction.DESC, "datetime"));
            return ResponseResult.enSuccess().add("recordsTotal", loginRecordRepository.count())
                    .add("recordsFiltered", loginRecordPage.getTotalElements()).add("data"
                            , loginRecordEntity2Vo(loginRecordPage.getContent()));
        } catch (Exception e) {
            log.warn("获取登录记录失败！msg {} ", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public ResponseResult getLoginRecordListForPage(int page, int size) {
        try {
            Page<LoginRecordEntity> loginRecordPage = loginRecordRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "datetime"));
            return ResponseResult.enSuccess().add("recordsTotal", loginRecordRepository.count())
                    .add("recordsFiltered", loginRecordPage.getTotalElements()).add("data"
                            , loginRecordEntity2Vo(loginRecordPage.getContent()));
        } catch (Exception e) {
            log.warn("获取登录记录失败！msg {} ", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public List<LoginRecordVo> getLoginRecordByUserForPage(Integer userId, int page, int size) {
        try {
            Page<LoginRecordEntity> loginRecordPage = loginRecordRepository.findByUser_Id(userId, PageRequest.of(page, size, Sort.Direction.DESC, "datetime"));
            return loginRecordEntity2Vo(loginRecordPage.getContent());
        } catch (Exception e) {
            log.warn("获取登录记录失败！msg {} ", e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public boolean deleteLoginRecord(Long loginRecordId) {
        try {
            loginRecordRepository.deleteById(loginRecordId);
            return true;
        } catch (Exception e) {
            log.warn("登录记录删除失败！id : {}", loginRecordId);
        }
        return false;
    }

    /**
     * Entity转Vo对象
     *
     * @param loginRecords LoginRecordEntity for Collection
     * @return LoginRecordVo for list
     */
    @NotNull
    private List<LoginRecordVo> loginRecordEntity2Vo(@NotNull Collection<LoginRecordEntity> loginRecords) {
        List<LoginRecordVo> loginRecordVos = new ArrayList<>();
        loginRecords.forEach(l -> loginRecordVos.add(new LoginRecordVo().setId(l.getId())
                .setUserId(l.getUser().getId())
                .setUserName(l.getUser().getName())
                .setLoginIp(l.getLoginIp())
                .setDatetime(l.getDatetime())
                .setProvince(l.getProvince())
                .setProvinceCode(l.getProvinceCode())
                .setCity(l.getCity())
                .setCityCode(l.getCityCode())
                .setAddress(l.getAddress())
                .setAgent(l.getAgent())));
        return loginRecordVos;
    }
}
