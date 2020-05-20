// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/23.
package cn.ohms.subsystem.validation.annotation;


import cs.ohmsubsystem.validation.NSEmailValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证邮箱地址
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NSEmailValidation.class)
@Documented
public @interface NSEmail {
    String message() default "Email address format error";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
