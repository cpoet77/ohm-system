// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/19.
package cn.ohms.subsystem.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import cs.ohmsubsystem.common.ResponseResult;
import cs.ohmsubsystem.entity.ResourceEntity;
import cs.ohmsubsystem.entity.UserEntity;
import cs.ohmsubsystem.repository.ResourceRepository;
import cs.ohmsubsystem.service.AppService;
import cs.ohmsubsystem.service.ResourceService;
import cs.ohmsubsystem.utils.JsonUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * ResourceService实现
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see cs.ohmsubsystem.service.ResourceService
 */
@Service("resourceServiceImpl")
public class ResourceServiceImpl implements ResourceService {
    /**
     * 数据库字段名
     */
    private final static String COMMON_FILE_FORMAT_FIELD_NAME = "commonFileFormat";
    /**
     * 数据库字段名
     */
    private final static String CONFIDENTIAL_FILE_FORMAT_FIELD_NAME = "confidentialFileFormat";

    private AppService appService;
    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceServiceImpl(AppService appService, ResourceRepository resourceRepository) {
        this.appService = appService;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Set<String> findCommonFileFormat() {
        String commonFileFormatString = appService.getAsString(COMMON_FILE_FORMAT_FIELD_NAME);
        Set<String> format = JsonUtil.decode(commonFileFormatString, new TypeReference<Set<String>>() {
        });
        return (format == null ? new HashSet<>() : format);
    }

    @Override
    public Set<String> findConfidentialFileFormat() {
        String confidentialFileFormatString = appService.getAsString(CONFIDENTIAL_FILE_FORMAT_FIELD_NAME);
        Set<String> format = JsonUtil.decode(confidentialFileFormatString, new TypeReference<Set<String>>() {
        });
        return (format == null ? new HashSet<>() : format);
    }

    @Override
    @Cacheable(cacheNames = {"common"}, key = "#format")
    /* 使用缓存，在缓存未过期之前得到的结果不变。 */
    public boolean isCommonFileFormat(String format) {
        return findCommonFileFormat().contains(format);
    }

    @Override
    @Cacheable(cacheNames = {"common"}, key = "#format")
    public boolean isConfidentialFileFormat(String format) {
        return findConfidentialFileFormat().contains(format);
    }

    @Override
    public ResponseResult saveConfidentialResource(@NotNull UserEntity user, @NotEmpty String uuid, @NotEmpty String name
            , @NotEmpty String suffix, @NotEmpty String path, boolean isPublic) {
        ResourceEntity resource = new ResourceEntity();
        resource.setId(uuid);
        resource.setName(name);
        resource.setSuffix(suffix);
        resource.setPublicity(isPublic);
        resource.setPath(path);
        resource.setUser(user);
        resource.setDatetime(LocalDateTime.now());
        return ResponseResult.enSuccess().add("resource", resourceRepository.save(resource));
    }

    @Override
    public File getResourceFile(UserEntity user, String resourceId, String fileName, String fix) {
        ResourceEntity resource = resourceRepository.findByIdAndSuffix(resourceId, fix);
        if (resource == null || !resource.getName().equals(fileName)) {
            return null;
        }
        File file = new File(resource.getPath());
        if (file.exists() && file.isFile() && file.canRead() && haveAccessTo(user, resource)) {
            return file;
        }
        return null;
    }

    @Override
    public boolean haveAccessTo(UserEntity user, @NotNull ResourceEntity resource) {
        return true;
//        if (resource.getPublicity() || user.getId().equals(resource.getUser().getId())) {
//            return true;
//        }
        /* 进一步验证是否属于课群资源,学生角色 */

        /* 进一步验证是否是老师下载资源 */

//        return false;
    }
}
