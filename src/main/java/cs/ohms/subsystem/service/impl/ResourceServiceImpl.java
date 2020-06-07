// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service.impl;

import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.core.DefaultExcelReader;
import com.github.liaochong.myexcel.utils.FileExportUtil;
import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.ResourceEntity;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.repository.ResourceRepository;
import cs.ohms.subsystem.repository.UserRepository;
import cs.ohms.subsystem.service.AppService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.utils.FileUtil;
import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.viewobject.LogFileInfoVo;
import cs.ohms.subsystem.viewobject.ResourceVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see ResourceService
 */
@Service("resourceService")
@Slf4j
public class ResourceServiceImpl implements ResourceService {
    /**
     * 公共文件支持的后缀
     */
    private final static Set<String> COMMON_FILE_FORMAT_SET = new HashSet<>();

    /**
     * 受保护文件支持的后缀
     */
    private final static Set<String> CONFIDENTIAL_FILE_FORMAT_SET = new HashSet<>();

    private AppService appService;

    private ResourceRepository resourceRepository;

    private UserRepository userRepository;

    private final static String LOG_DIR = "logs";

    @Autowired
    public ResourceServiceImpl(AppService appService, ResourceRepository resourceRepository, UserRepository userRepository) {
        this.appService = appService;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        String uploadCommonPattern = appService.getAsString("ohms.upload.common.pattern");
        String uploadConfidentialPattern = appService.getAsString("ohms.upload.confidential.pattern");
        if (!NStringUtil.isEmpty(uploadCommonPattern)) {
            for (String pattern : uploadCommonPattern.split(",")) {
                COMMON_FILE_FORMAT_SET.add(NStringUtil.joint(".{}", pattern));
            }
        }
        if (!NStringUtil.isEmpty(uploadConfidentialPattern)) {
            for (String pattern : uploadConfidentialPattern.split(",")) {
                CONFIDENTIAL_FILE_FORMAT_SET.add(NStringUtil.joint(".{}", pattern));
            }
        }
    }

    @Override
    public <T> List<T> inputStreamToTable(Class<T> clazz, InputStream in) throws Exception {
        return (DefaultExcelReader.of(clazz).sheet(0).rowFilter(row -> row.getRowNum() > 0).read(in));
    }

    @Override
    public <T> void dataExportToTableFile(List<T> data, Class<T> clazz, @NotNull File file) throws IOException {
        File fileDir = file.getParentFile();
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                throw new IOException("创建目录失败！");
            }
        }
        FileExportUtil.export(DefaultExcelBuilder.of(clazz).build(data), file);
    }

    @Override
    public boolean isCommonFileFormat(String fix) {
        return COMMON_FILE_FORMAT_SET.contains(fix);
    }

    @Override
    public boolean isConfidentialFileFormat(String fix) {
        return CONFIDENTIAL_FILE_FORMAT_SET.contains(fix);
    }

    @Override
    public int calculatePageNum(int start, int size) {
        return ((int) Math.ceil((double) start / size));
    }

    @Override
    public boolean isDemandXlsFile(@NotNull MultipartFile file) {
        return (!file.isEmpty() && ".xlsx".equals(FileUtil.getFilePostfix(file.getOriginalFilename())));
    }

    @Override
    public List<LogFileInfoVo> getAllLogFileList() {
        File dir = new File(LOG_DIR);
        if (dir.exists() && dir.isDirectory() && dir.canRead()) {
            List<LogFileInfoVo> logFileInfoVos = new ArrayList<>();
            for (File file : Objects.requireNonNull(dir.listFiles(((d, name) -> Pattern.matches("^log[0-9\\-]*\\.txt$", name))))) {
                logFileInfoVos.add(new LogFileInfoVo().setFileName(file.getName()).setSize(file.length()));
            }
            return logFileInfoVos;
        }
        log.warn("不能正确获取日志列表！");
        return null;
    }

    @Override
    @Cacheable(cacheNames = {"common"}, key = "#fileName")
    public String getLogFileContent(String fileName) {
        try {
            return FileUtil.readFileAsString(NStringUtil.joint("{}/{}", LOG_DIR, fileName));
        } catch (IOException e) {
            log.info("读取日志文件失败！fileName : {}", fileName);
        }
        return null;
    }

    @Override
    public ResponseResult saveConfidentialResource(UserEntity user, String uuid, String name, String fix, String path, Boolean isPublic) {
        ResourceEntity resource = new ResourceEntity().setId(uuid).setName(name).setSuffix(fix).setPath(path).setPublicity(isPublic).setUser(user);
        try {
            return ResponseResult.enSuccess().add("resource", resource2vo(resourceRepository.save(resource)));
        } catch (Exception e) {
            log.warn("保存资源信息失败！msg : {}", e.getLocalizedMessage());
        }
        return ResponseResult.enFail();
    }

    @Override
    public boolean deleteResource(UserEntity user, String resourceId) {
        Optional<ResourceEntity> resourceOpt = resourceRepository.findByIdAndUser(resourceId, user);
        try {
            if (resourceOpt.isPresent()) {
                ResourceEntity resource = resourceOpt.get();
                File file = new File(resource.getPath());
                if (file.exists() && file.isFile() && file.canWrite() && file.delete()) {
                    resourceRepository.delete(resource);
                    return true;
                }
            }
        } catch (Exception e) {
            log.warn("资源删除失败！uuid : {}", resourceId);
        }
        return false;
    }

    @Override
    public File getResourceFile(UserEntity user, String resourceId, String fileName, String fix) {
        Optional<ResourceEntity> resourceOpt = resourceRepository.findByIdAndSuffix(resourceId, fix);
        if (resourceOpt.isPresent()) {
            ResourceEntity resource = resourceOpt.get();
            if (!resource.getName().equals(fileName) || !haveAccessTo(user.getId(), resource.getId())) {
                return null;
            }
            File file = new File(resource.getPath());
            if (file.exists() && file.isFile() && file.canRead()) {
                return file;
            }
        }
        return null;
    }

    @Override
    public boolean haveAccessTo(Integer userId, String resourceId) {
//        Optional<UserEntity> userOpt = userRepository.findById(userId);
//        Optional<ResourceEntity> resourceOpt = resourceRepository.findById(resourceId);
//        if (userOpt.isPresent() && resourceOpt.isPresent()) {
//            UserEntity user = userOpt.get();
//            ResourceEntity resource = resourceOpt.get();
//            if (resource.getPublicity() || user.getId().equals(resource.getUser().getId())) {/* 资源公开或者下载的用户本身是拥有者 */
//                return true;
//            }
//        }
//        return false;
        return true;/* 禁止启用 */
    }

    @Override
    public List<ResourceEntity> findAllByResourceId(Collection<String> ids) {
        List<ResourceEntity> resources = resourceRepository.findAllById(ids);
        return resources.size() == ids.size() ? resources : null;
    }

    @Override
    public List<ResourceEntity> findAllByResourceId(@NotNull String ids) {
        return findAllByResourceId(Arrays.asList(ids.split(",")));
    }


    /**
     * 实体对象转换为vo对象
     *
     * @param resource ResourceEntity
     * @return ResourceVo
     */
    private ResourceVo resource2vo(@NotNull ResourceEntity resource) {
        return (new ResourceVo().setId(resource.getId())
                .setName(resource.getName())
                .setUserId(resource.getUser().getId())
                .setDatetime(resource.getDatetime())
                .setPublicity(resource.getPublicity())
                .setSuffix(resource.getSuffix()));
    }
}
