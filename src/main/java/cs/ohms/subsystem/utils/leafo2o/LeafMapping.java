// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/14.
package cs.ohms.subsystem.utils.leafo2o;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Modification of attribute mapping
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LeafMapping {
    /**
     * 目标属性名称
     *
     * @return targetName
     */
    String targetName() default "";

    /**
     * 是否使用get/set方法
     *
     * @return method
     */
    boolean method() default false;

    /**
     * 指示get方法名
     *
     * @return get method name
     */
    String get() default "";

    /**
     * 指示set方法名
     *
     * @return set method name
     */
    String set() default "";

    /**
     * 指示目标get方法名
     *
     * @return target get method name
     */
    String targetGet() default "";

    /**
     * 指示目标set方法名
     *
     * @return target set method name
     */
    String targetSet() default "";

    /**
     * 使用的转换器
     *
     * @return LeafConvertField
     */
    Class<? extends LeafConvertField> converter() default SimpleConvertField.class;

    /**
     * 使用的场景
     *
     * @return groups
     */
    Class<?>[] groups() default {};
}
