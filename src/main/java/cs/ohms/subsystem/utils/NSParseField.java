// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/7.
package cs.ohms.subsystem.utils;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动注入参数到属性
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NSParseField {
    /**
     * 指定属性名，用于与map中的值对应
     * <p><b>如果没有指定该值，将</b></p>
     * @return name 属性名
     */
    String name() default "";

    /**
     * 默认值
     * <p>当没有设定值的时候，该值将被注入</p>
     * <p><b>@%#%代表不需要默认值，如果需要使用@%#%请使用set方法</b></p>
     * @return value 默认值
     */
    String value() default "@%#%";

    /**
     * 区分大小写
     * @return true|false
     */
    boolean matchCase() default true;

    /**
     * 转换驼峰命名
     * <p><b>userName将与user_name匹配</b></p>
     * @return true|false
     */
    boolean conversion() default false;

    /**
     * 验证场景
     * @return groups scene
     */
    Class<?>[] groups() default {};
}
