// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/10.
package cn.ohms.subsystem.utils.image;

/**
 * 图片操作异常
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class ImageUtilException extends Exception {
    /**
     * 构造器
     */
    public ImageUtilException() {
    }

    /**
     * 构造器
     *
     * @param s 错误信息
     */
    public ImageUtilException(String s) {
        super(s);
    }

    /**
     * 构造器
     *
     * @param s         错误信息
     * @param throwable Throwable
     */
    public ImageUtilException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * 构造器
     *
     * @param throwable Throwable
     */
    public ImageUtilException(Throwable throwable) {
        super(throwable);
    }
}
