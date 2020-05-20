// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/19.
package cn.ohms.subsystem.service;

import cs.ohmsubsystem.common.ResponseResult;
import cs.ohmsubsystem.entity.ResourceEntity;
import cs.ohmsubsystem.entity.UserEntity;

import java.io.File;
import java.util.Set;

/**
 * ResourceService
 *
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
     * 获取系统支持的普通文件格式
     *
     * @return set
     */
    Set<String> findCommonFileFormat();

    /**
     * 获取系统支持的保护文件格式
     *
     * @return set
     */
    Set<String> findConfidentialFileFormat();

    /**
     * 验证格式是否受支持
     *
     * @param format 普通资源文件格式
     * @return true|false
     */
    boolean isCommonFileFormat(String format);

    /**
     * 验证格式是否受支持
     *
     * @param format 受保护的资源文件格式
     * @return true|false
     */
    boolean isConfidentialFileFormat(String format);

    /**
     * 保存资源信息
     *
     * @param user     上传用户，拥有者
     * @param uuid     资源uuid
     * @param name     资源名
     * @param suffix   资源后缀
     * @param path     物理路径
     * @param isPublic 是否公开
     * @return ResponseResult
     */
    ResponseResult saveConfidentialResource(UserEntity user, String uuid, String name, String suffix, String path, boolean isPublic);

    /**
     * 获取受保护的资源文件
     * <p>前提条件：文件存在且可读，用户具有读取的权力，否则返回null</p>
     *
     * @param user       user
     * @param resourceId 资源id,为32bit的uuid
     * @param fix        文件后缀，类型
     * @return File|null
     */
    File getResourceFile(UserEntity user, String resourceId, String fileName, String fix);

    /**
     * 判断用户是否有权读取某个文件
     *
     * @param user     UserEntity
     * @param resource ResourceEntity
     * @return true|false
     */
    boolean haveAccessTo(UserEntity user, ResourceEntity resource);
}
