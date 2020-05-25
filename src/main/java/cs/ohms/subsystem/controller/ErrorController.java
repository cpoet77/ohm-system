// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/3.
package cs.ohms.subsystem.controller;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.exception.NSRuntimeException;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.utils.ValidatorMsgUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * 访问错误
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@ControllerAdvice
@Controller
@RequestMapping("/error")
public class ErrorController {
    private final static Logger log = LoggerFactory.getLogger(ErrorController.class);

    /**
     * 请求错误返回404
     *
     * @return HttpStatus.NOT_FOUND
     */
    @ExceptionHandler(Exception.class)
    @GetMapping("/404")
    public String error(Exception e) {
        log.error(NStringUtil.joint("{}{}{}", "There is an exception in the system operation. "
                , "Please check the operation log and test the system environment.", "this by nsleaf！"), e);
        return "error/404";
    }

    /**
     * 捕获手动抛出的NSRuntimeException
     *
     * @param e NSRuntimeException
     * @return 404
     */
    @ExceptionHandler(NSRuntimeException.class)
    @GetMapping("/nsRuntimeException")
    public String nsRuntimeException(@NotNull NSRuntimeException e) {
        log.info("Manually thrown exception caught : {}", e.getMessage());
        return "error/404";
    }

    /**
     * 网络服务错误500
     *
     * @return HttpStatus.INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @GetMapping("/500")
    public String serverError() {
        return "error/500";
    }

    /**
     * 捕获ConstraintViolationException
     *
     * @param e ConstraintViolationException
     * @return ResponseResult
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @PostMapping("/violationExceptionPost")
    @ResponseBody
    public ResponseResult violationExceptionPost(@NotNull ConstraintViolationException e) {
        log.info("data validation : {}", e.getMessage());
        return ResponseResult.enFail().add("error list", ValidatorMsgUtil.toList(e.getConstraintViolations()));
    }

    /**
     * 权限认证错误处理
     *
     * @param e       UnauthenticatedException
     * @param request HttpServletRequest
     * @return url or view
     */
    @ExceptionHandler(UnauthenticatedException.class)
    @RequestMapping("/unauthenticatedException")
    public String unauthenticatedException(@NotNull UnauthenticatedException e, @NotNull HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        String url = request.getRequestURL().toString();
        log.info("{}, url : {}", e.getLocalizedMessage(), url);
        if (subject.isAuthenticated()) {
            return "error/404";
        } else {
            return NStringUtil.joint("redirect:/login?backUrl={}", url);
        }
    }
}
