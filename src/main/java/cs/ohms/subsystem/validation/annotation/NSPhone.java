// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/23.
package cs.ohms.subsystem.validation.annotation;


import cs.ohms.subsystem.validation.NSPhoneValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证手机号
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NSPhoneValidation.class)
@Documented
public @interface NSPhone {
    String message() default "Mobile number error, the system only supports domestic 11 digit number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
