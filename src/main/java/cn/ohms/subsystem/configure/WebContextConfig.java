// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/14.
package cn.ohms.subsystem.configure;

import cs.ohmsubsystem.common.OhmsLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web上下文配置
 * <p><b>注册拦截器，对数据进行统一的拦截处理</b></p>
 * <p> * Web配置文件                 </p>
 * <p> * Author ASorb               </p>
 * <p> * EmailBasic asorb@qq.com         </p>
 * <p> * CrateDate 2019/11/11       </p>
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Configuration
public class WebContextConfig implements WebMvcConfigurer {
    /**
     * 注册区域解析
     * @return LocaleResolver
     */
    @Bean("localeResolver")
    public LocaleResolver  localeResolver(){
        return new OhmsLocaleResolver("en");
    }
}
