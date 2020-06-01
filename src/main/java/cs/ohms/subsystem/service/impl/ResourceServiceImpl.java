// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service.impl;

import com.github.liaochong.myexcel.core.DefaultExcelReader;
import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.UserEntity;
import cs.ohms.subsystem.service.AppService;
import cs.ohms.subsystem.service.ResourceService;
import cs.ohms.subsystem.utils.FileUtil;
import cs.ohms.subsystem.utils.NStringUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see ResourceService
 */
@Service("resourceService")
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

    @Autowired
    public ResourceServiceImpl(AppService appService) {
        this.appService = appService;
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
    public boolean isCommonFileFormat(String fix) {
        return COMMON_FILE_FORMAT_SET.contains(fix);
    }

    @Override
    public boolean isConfidentialFileFormat(String fix) {
        return CONFIDENTIAL_FILE_FORMAT_SET.contains(fix);
    }

    @Override
    public boolean isDemandXlsFile(@NotNull MultipartFile file) {
        return (!file.isEmpty() && ".xlsx".equals(FileUtil.getFilePostfix(file.getOriginalFilename())));
    }

    @Override
    public ResponseResult saveConfidentialResource(UserEntity attribute, String uuid, String name, String fix, String path, Boolean isPublic) {
        return null;
    }
}
