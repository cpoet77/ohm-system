// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/3/26.
package cs.ohms.subsystem.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.PathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

/**
 * spring配置
 * 重要的功能和部分bean注册将在代码中实现，减少对配置文档的依赖
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@SpringBootConfiguration
@EnableCaching
@EnableAsync
public class SpringConfig {
    private static final Logger log = LoggerFactory.getLogger(SpringConfig.class);

    @Value("${spring.servlet.multipart.max-file-size}")
    private long uploadFileSize;

    /**
     * 文件上传解析器
     *
     * @return CommonsMultipartResolver 文件上传适配器
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        /* 默认编码 */
        multipartResolver.setDefaultEncoding("UTF-8");
        /* 上传最大大小(B)，默认10MB */
        multipartResolver.setMaxUploadSize(uploadFileSize);
        /* 内存临时占有最大大小 */
        multipartResolver.setMaxInMemorySize(40960);
        /* 上传临时路径 */
        try {
            multipartResolver.setUploadTempDir(new PathResource("upload/temp"));
        } catch (IOException e) {
            log.error("File upload temporary directory failed to open", e);
        }
        /* 延迟文件解析 */
        multipartResolver.setResolveLazily(true);
        return multipartResolver;
    }
}
