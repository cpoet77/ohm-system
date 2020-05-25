// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service;

import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface ResourceService {
    /**
     * 传入inputStream转换成table
     *
     * @param clazz Class
     * @param in    InputStream
     * @param <T>   类型
     * @return list
     */
    <T> List<T> inputStreamToTable(Class<T> clazz, InputStream in) throws Exception;
}
