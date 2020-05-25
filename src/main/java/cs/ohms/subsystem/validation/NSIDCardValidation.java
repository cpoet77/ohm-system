// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/23.
package cs.ohms.subsystem.validation;


import cs.ohms.subsystem.validation.annotation.NSIDCard;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 对身份证进行验证
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSIDCardValidation implements ConstraintValidator<NSIDCard, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(null == value){
            return true;
        }
        String regex = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";
        return value.matches(regex);
    }
}
