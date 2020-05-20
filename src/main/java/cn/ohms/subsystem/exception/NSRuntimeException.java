// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/26.
package cn.ohms.subsystem.exception;

/**
 * 该异常会被捕获，但是不会输出详细信息到日志中
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSRuntimeException extends RuntimeException{
    public NSRuntimeException() {
        super();
    }

    public NSRuntimeException(String s) {
        super(s);
    }

    public NSRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NSRuntimeException(Throwable throwable) {
        super(throwable);
    }

    protected NSRuntimeException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
