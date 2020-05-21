// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/19.
package cn.ohms.subsystem.validation.annotation;


import cn.ohms.subsystem.validation.NSCharCheckValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 数据校验之check约束
 * <p><b>主要针对char型数据</b></p>
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NSCharCheckValidation.class)
@Documented
public @interface NSCharCheck {
    String message() default "check constraint not satisfied";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    char[] value();
}
