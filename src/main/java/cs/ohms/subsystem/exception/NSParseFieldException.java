// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/7.
package cs.ohms.subsystem.exception;

/**
 * 解析参数异常
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSParseFieldException extends NSRuntimeException{
    public NSParseFieldException() {
    }

    public NSParseFieldException(String message) {
        super(message);
    }

    public NSParseFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public NSParseFieldException(Throwable cause) {
        super(cause);
    }

    public NSParseFieldException(String message, Throwable cause, boolean enableSuppression
            , boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
