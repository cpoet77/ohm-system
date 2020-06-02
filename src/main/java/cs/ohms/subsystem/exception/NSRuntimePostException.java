// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/26.
package cs.ohms.subsystem.exception;

/**
 * 该异常会被捕获，但是不会输出详细信息到日志中
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSRuntimePostException extends NSRuntimeException{
    public NSRuntimePostException() {
        super();
    }

    public NSRuntimePostException(String s) {
        super(s);
    }

    public NSRuntimePostException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NSRuntimePostException(Throwable throwable) {
        super(throwable);
    }

    protected NSRuntimePostException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
