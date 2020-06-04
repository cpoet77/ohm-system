// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/8.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.UserEntity;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface LoginService {
    /**
     * 登录记录
     * <p><b>开启异步，不考虑记录结果</b></p>
     *
     * @param request   HttpServletRequest
     * @param user      UserEntity
     * @param userAgent User-Agent
     */
    @Async
    void loginRecord(HttpServletRequest request, UserEntity user, String userAgent);

    /**
     * 根据Like用户名分页查询登录记录
     *
     * @param userName 用户名
     * @param page     页号
     * @param size     数量
     * @return ResponseResult
     */
    ResponseResult getLoginRecordListByUserNameIsLikeFoPage(String userName, int page, int size);

    /**
     * 获取登录日志
     *
     * @param page 页号
     * @param size 数量
     * @return ResponseResult
     */
    ResponseResult getLoginRecordListForPage(int page, int size);

    /**
     * 根据id删除登录记录
     *
     * @param loginRecordId 登录记录id
     * @return true|false
     */
    boolean deleteLoginRecord(Long loginRecordId);
}
