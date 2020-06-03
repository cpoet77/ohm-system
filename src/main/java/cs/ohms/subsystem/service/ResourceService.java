// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 判断是否是支持的公共文件后缀
     *
     * @param fix 文件后缀
     * @return true|false
     */
    boolean isCommonFileFormat(String fix);

    /**
     * 判断是否是受保护的文件后缀
     *
     * @param fix 文件后缀
     * @return true|false
     */
    boolean isConfidentialFileFormat(String fix);

    /**
     * 计算页码
     *
     * @param start 起点
     * @param size  每一页的数量
     * @return page num
     */
    int calculatePageNum(int start, int size);

    /**
     * 判断上传的表格文件是否满足要求
     *
     * @param file MultipartFile
     * @return true|false
     */
    boolean isDemandXlsFile(MultipartFile file);

    ResponseResult saveConfidentialResource(UserEntity attribute, String uuid, String name, String fix, String path, Boolean isPublic);
}
