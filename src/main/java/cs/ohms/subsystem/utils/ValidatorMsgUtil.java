// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/19.
package cs.ohms.subsystem.utils;

import org.jetbrains.annotations.NotNull;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 数据校验信息处理工具
 * <p><b>拼接校验信息，返回便捷的格式</b></p>
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class ValidatorMsgUtil {

    /**
     * 返回验证错误的list集合，没有错误返回null
     * @param set 原有验证集合
     * @return null|list集合
     */
    @NotNull
    public static List<String> toList(@NotNull Set<ConstraintViolation<?>> set){
        List<String> m = new ArrayList<>();
        for(ConstraintViolation<?> constraintValidator : set){
            m.add(constraintValidator.getMessage());
        }
        return m;
    }

    /**
     * 返回验证错误的list集合，没有错误返回null
     * @param set 原有验证集合
     * @return null|list集合
     */
    @NotNull
    public static <T> List<String> toTList(@NotNull Set<ConstraintViolation<T>> set){
        List<String> m = new ArrayList<>();
        for(ConstraintViolation<T> constraintValidator : set){
            m.add(constraintValidator.getMessage());
        }
        return m;
    }
}
