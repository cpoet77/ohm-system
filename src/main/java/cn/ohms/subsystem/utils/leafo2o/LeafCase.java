// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/14.
package cn.ohms.subsystem.utils.leafo2o;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Modify the class
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LeafCase {
    /**
     * 自定义的转换器
     * <p><b>注意：使用该注解以后，对于属性将不在进行扫描，直接委托转换器进行处理</b></p>
     *
     * @return LeafConvertCase
     */
    Class<? extends LeafConvertCase<?, ?>> converter() default SimpleConvertCase.class;

    /**
     * 使用的场景
     *
     * @return groups
     */
    Class<?>[] groups() default {};
}
