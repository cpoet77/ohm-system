// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/19.
package cs.ohms.subsystem.validation;


import cs.ohms.subsystem.validation.annotation.NSCharCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义数据验证器
 * <p><b>Char型数据验证器</b></p>
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSCharCheckValidation implements ConstraintValidator<NSCharCheck, Character> {
    private NSCharCheck check;

    @Override
    public void initialize(NSCharCheck constraintAnnotation) {
        this.check = constraintAnnotation;
    }

    @Override
    public boolean isValid(Character value, ConstraintValidatorContext context) {
        if(null == value){      /* 丢给其它验证器 */
            return true;
        }
        for (char c : check.value()) {
            if (value == c) {
                return true;
            }
        }
        return false;
    }
}
