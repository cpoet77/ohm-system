// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/23.
package cs.ohms.subsystem.validation;


import cs.ohms.subsystem.utils.NStringUtil;
import cs.ohms.subsystem.validation.annotation.NSEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 对邮箱地址进行验证
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSEmailValidation implements ConstraintValidator<NSEmail, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (NStringUtil.isEmpty(value)) {
            return true;
        }
        String regex = "^[a-zA-Z][\\w.]{1,30}@[a-zA-Z]\\w{1,50}\\.((cn)|(com)|(org))$";
        return value.matches(regex);
    }
}
