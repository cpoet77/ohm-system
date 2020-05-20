// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/3/29.
package cn.ohms.subsystem.service.impl;

import cs.ohmsubsystem.service.AppService;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * AppService实现
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see cs.ohmsubsystem.service.AppService
 */
@Service("AppService")
public class AppServiceImpl implements AppService {
    private final static Logger log = LoggerFactory.getLogger(AppServiceImpl.class);
    // 获取调用jar引入的参数args
    // 用于动态设定一些值
    @Resource
    private ApplicationArguments args;
    // 所有配置信息
    private final static Properties appInfo = new Properties();

    private SystemRepository systemRepository;

    @Autowired
    public AppServiceImpl(SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    /**
     * 加载配置
     * <p>加载的配置信息分为几个部分,具体参照代码</p>
     */
    @PostConstruct
    private void load() {
        log.info("Loading system related information ...");
        parseArgs();            // 解析传入参数
        setSystemProperty();    // 设定系统信息
        setOHMSMsg();           // OHMS相关信息
    }

    /**
     * 解析传入系统的args
     * 如果取与默认属性相同的名称，原有值将被覆盖
     * 格式: -key=value
     * 使用： appService.get("key")
     */
    private void parseArgs() {
        log.info("Parsing option parameters ...");
        for (String arg : args.getNonOptionArgs()) {
            // 匹配格式是否正确
            if (Pattern.matches("^-[^-=\\s]+=[^-=\\s]+$", arg)) {
                // 截取字符串
                String[] result = arg.substring(1).split("=");
                appInfo.put(result[0], result[1]);
            }
        }
    }

    /**
     * 设定系统信息
     * 为防止被覆盖，应放在最后设定
     */
    private void setSystemProperty() {
        log.info("Get platform related properties ...");
        appInfo.put("osName", System.getProperty("os.name"));                         // 当前运行的系统名称
        appInfo.put("osArch", System.getProperty("os.arch"));                         // 当前运行的系统架构
        appInfo.put("osVersion", System.getProperty("os.version"));                   // 当前运行的系统版本
        appInfo.put("javaVersion", System.getProperty("java.version"));               // 当前运行的java版本
        appInfo.put("javaClassPath", System.getProperty("java.class.path"));         // java Class 路径
        appInfo.put("userName", System.getProperty("user.name"));                    // 用户名
        appInfo.put("userHome", System.getProperty("user.home"));                    // 用户家目录
        appInfo.put("userDir", System.getProperty("user.dir"));                      // 当前用户工作路径
    }

    /**
     * 设定OHMS相关信息
     */
    private void setOHMSMsg() {
        log.info("Set OHMS information ...");
        appInfo.put("ohmsName", "OHMSystem");
        appInfo.put("version", "1.0.0");
        appInfo.put("ohmsNameFormat", "<b>OHMS</b>ystem");
        appInfo.put("ohmsNameEN", "Online homework management system");
        appInfo.put("ohmsNameZH", "在线作业管理系统");
    }

    /**
     * 读取系统表 system table
     * <p><b>如果系统表存在该属性，则读出来后会保存起来。</b></p>
     *
     * @param name 属性名
     * @return value | null
     */
    @Nullable
    private String readSystemTable(String name) {
        Optional<SystemEntity> optionalSystemEntity = systemRepository.findById(name);
        if (!optionalSystemEntity.isPresent()) {
            return null;
        }
        SystemEntity systemEntity = optionalSystemEntity.get();
        String value = systemEntity.getValue();
        appInfo.put(name, value);
        return value;
    }

    @Override
    public String getName() {
        return getAsString("ohmsName");
    }

    @Override
    public String getVersion() {
        return getAsString("version");
    }

    @Override
    public String getDomain() {
        return getAsString("siteDomain");
    }

    @Override
    public Object get(String name) {
        if (contain(name)) {
            return appInfo.get(name);
        }
        return readSystemTable(name);
    }

    @Override
    public String getAsString(String name) {
        return String.valueOf(get(name));
    }

    @Override
    public int getAsInt(String name) {
        return Integer.parseInt(getAsString(name));
    }

    @Override
    public long getAsLong(String name) {
        return Long.parseLong(getAsString(name));
    }

    @Override
    public float getAsFloat(String name) {
        return Float.parseFloat(getAsString(name));
    }

    @Override
    public boolean contain(String name) {
        return appInfo.containsKey(name);
    }

    @Override
    public Set<String> propertyNames() {
        return appInfo.stringPropertyNames();
    }

    @Override
    public void reload() {
        // 清空所有属性
        appInfo.clear();
        // 加载新的属性
        this.load();
    }
}
