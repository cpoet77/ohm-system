// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/19.
package cs.ohms.subsystem.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * 数据校验器配置
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Configuration
public class ValidationConfig {
    /**
     * 返回校验器工厂
     * @return ValidatorFactory
     */
    @Bean("validatorFactory")
    public ValidatorFactory validatorFactory(){
        return (Validation.buildDefaultValidatorFactory());
    }
}
