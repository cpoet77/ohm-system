// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/11.
package cn.ohms.subsystem.utils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * 使用freemarker模板引擎读取模板并完成替换
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class FreemarkerUtil {
    // 模板文件存储路径
    private final static String TEMP_PATH = "/templates";

    /**
     * 读取模板并完成替换后返回替换结果
     * @param file 模板文件
     * @param map 需要替换的值
     * @return 替换结果
     * @throws TemplateException TemplateException
     * @throws IOException IOException
     */
    @NotNull
    public static String analysis(String file, Map<String, Object> map) throws TemplateException, IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        TemplateLoader loader = new ClassTemplateLoader(FreemarkerUtil.class, TEMP_PATH);
        configuration.setTemplateLoader(loader);
        Template template = configuration.getTemplate(file);
        StringWriter stringWriter = new StringWriter();
        Writer writer = new BufferedWriter(stringWriter);
        template.process(map, writer);
        String text = stringWriter.getBuffer().toString();
        writer.close();
        stringWriter.close();
        return text;
    }
}
