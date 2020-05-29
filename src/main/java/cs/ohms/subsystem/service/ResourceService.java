// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.UserEntity;

import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface ResourceService {
    /**
     * 普通文件存储路径
     */
    String commonFilePath = "upload/common";

    /**
     * 受保护文件存储路径
     */
    String confidentialFilePath = "upload/confidential";

    /**
     * 传入inputStream转换成table
     *
     * @param clazz Class
     * @param in    InputStream
     * @param <T>   类型
     * @return list
     */
    <T> List<T> inputStreamToTable(Class<T> clazz, InputStream in) throws Exception;

    boolean isCommonFileFormat(String fix);

    boolean isConfidentialFileFormat(String fix);

    ResponseResult saveConfidentialResource(UserEntity attribute, String uuid, String name, String fix, String path, Boolean isPublic);
}
