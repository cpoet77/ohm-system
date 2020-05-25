// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/23.
package cs.ohms.subsystem.validation;


import cs.ohms.subsystem.validation.annotation.NSPhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 对手机号进行验证
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSPhoneValidation implements ConstraintValidator<NSPhone, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(null == value){
            return true;
        }
        String regex = "^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$";
        return value.matches(regex);
    }
}
