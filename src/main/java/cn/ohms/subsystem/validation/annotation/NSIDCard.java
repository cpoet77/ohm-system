// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/23.
package cn.ohms.subsystem.validation.annotation;


import cn.ohms.subsystem.validation.NSIDCardValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 身份证号验证
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NSIDCardValidation.class)
@Documented
public @interface NSIDCard {
    String message() default "Incorrect format of ID card";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
