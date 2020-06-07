// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.ResourceEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.viewobject.LogFileInfoVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
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
     * 将数据导出到表格文件
     *
     * @param <T>   class 类型
     * @param data  数据
     * @param clazz class
     * @param file  目标文件
     */
    <T> void dataExportToTableFile(List<T> data, Class<T> clazz, File file) throws IOException;

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

    /**
     * 获取所有日志文件列表
     *
     * @return LogFileInfoVo for list
     */
    List<LogFileInfoVo> getAllLogFileList();

    /**
     * 获取日志文件内容
     *
     * @param fileName fileName
     * @return content|null
     */
    String getLogFileContent(String fileName);

    /**
     * 保存受保护资源的信息入库
     *
     * @param user     拥有者
     * @param uuid     资源uuid
     * @param name     资源名
     * @param fix      资源后缀
     * @param path     资源路径
     * @param isPublic 是否公开资源
     * @return ResponseResult
     */
    ResponseResult saveConfidentialResource(UserEntity user, String uuid, String name, String fix, String path, Boolean isPublic);

    /**
     * 删除用户指定的资源
     *
     * @param user       目标用户
     * @param resourceId 资源id
     * @return true|false
     */
    boolean deleteResource(UserEntity user, String resourceId);

    /**
     * 获取文件对象，当没有权限访问或者资源不存在的时返回null
     *
     * @param user       用户
     * @param resourceId 资源id
     * @param fileName   文件名
     * @param fix        后缀
     * @return File|null
     */
    File getResourceFile(UserEntity user, String resourceId, String fileName, String fix);

    /**
     * 判断用户是否有权限访问指定资源
     *
     * @param userId     用户id
     * @param resourceId 资源id
     * @return true|false
     */
    boolean haveAccessTo(Integer userId, String resourceId);

    /**
     * 获取指定ids的资源实体
     *
     * @param ids ids
     * @return ResourceEntity for list
     */
    List<ResourceEntity> findAllByResourceId(Collection<String> ids);

    /**
     * 格式：id1,id2,id3...
     *
     * @param ids ids
     * @return ResourceEntity  for list
     */
    List<ResourceEntity> findAllByResourceId(String ids);
}
