// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/23.
package cn.ohms.subsystem.component;

import com.fasterxml.jackson.core.type.TypeReference;
import cs.ohmsubsystem.utils.JsonUtil;
import cs.ohmsubsystem.utils.ValidatorMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

/**
 * RequestScheme component
 * 为请求参数处理提供方法
 * <ol>
 *     <li>Json转换成目标对象</li>
 *     <li>json转换成目标对象并验证数据</li>
 *     <li>验证数据是否正确</li>
 *     <li>...</li>
 * </ol>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Component("requestSchemeCMP")
public class RequestSchemeCMP {
    // 验证器工厂
    private ValidatorFactory validatorFactory;

    @Autowired
    public RequestSchemeCMP(@NonNull ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    /**
     * json string转目标对象
     *
     * @param jsonStr json string
     * @param tClass  目标对象类型
     * @param <T>     泛型
     * @return 目标对象
     */
    public <T> T convert(String jsonStr, Class<T> tClass) {
        return JsonUtil.decode(jsonStr, tClass);
    }

    /**
     * json string 转目标对象
     *
     * @param jsonStr      json string
     * @param valueTypeRef TypeReference
     * @param <T>          目标对象类型
     * @return 目标对象
     */
    public <T> T convert(String jsonStr, TypeReference<T> valueTypeRef) {
        return JsonUtil.decode(jsonStr, valueTypeRef);
    }

    /**
     * 验证数据
     *
     * @param object 需要验证的对象
     * @param groups 验证的场景
     * @param <T>    类型
     * @return ConstraintViolation for set
     */
    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return getValidator().validate(object, groups);
    }

    /**
     * 验证数据并直接抛出异常
     *
     * @param object 需要验证的对象
     * @param groups 验证的场景
     * @param <T>    类型
     */
    public <T> void validateThrow(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> set = validate(object, groups);
        if (!set.isEmpty()) {
            throw new ConstraintViolationException(set);
        }
    }

    /**
     * 验证数据
     *
     * @param object       需要验证的对象
     * @param propertyName 验证属性的名称
     * @param groups       验证场景
     * @param <T>          类型
     * @return ConstraintViolation for set
     */
    public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
        return getValidator().validateProperty(object, propertyName, groups);
    }

    /**
     * 验证数据并直接抛出异常
     *
     * @param object       需要验证的对象
     * @param propertyName 验证属性的名称
     * @param groups       验证场景
     * @param <T>          类型
     */
    public <T> void validatePropertyThrow(T object, String propertyName, Class<?>... groups) {
        Set<ConstraintViolation<T>> set = validateProperty(object, propertyName, groups);
        if (!set.isEmpty()) {
            throw new ConstraintViolationException(set);
        }
    }

    /**
     * 验证数据
     *
     * @param beanType     目标类型
     * @param propertyName 验证属性的名称
     * @param value        属性的值
     * @param groups       验证场景
     * @param <T>          类型
     * @return ConstraintViolation for set
     */
    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value
            , Class<?>... groups) {
        return getValidator().validateValue(beanType, propertyName, value, groups);
    }

    /**
     * 验证数据并直接抛出异常
     *
     * @param beanType     目标类型
     * @param propertyName 验证属性的名称
     * @param value        属性的值
     * @param groups       验证场景
     * @param <T>          类型
     */
    public <T> void validateValueThrow(Class<T> beanType, String propertyName, Object value
            , Class<?>... groups) {
        Set<ConstraintViolation<T>> set = validateValue(beanType, propertyName, value, groups);
        if (!set.isEmpty()) {
            throw new ConstraintViolationException(set);
        }
    }

    /**
     * 验证数据
     *
     * @param object 目标对象
     * @param groups 验证场景
     * @param <T>    类型
     * @return String for list
     */
    public <T> List<String> validateMsg(T object, Class<?>... groups) {
        return ValidatorMsgUtil.toTList(validate(object, groups));
    }

    /**
     * 验证数据
     *
     * @param object       目标对象
     * @param propertyName 验证的属性
     * @param groups       验证场景
     * @param <T>          类型
     * @return String for list
     */
    public <T> List<String> validatePropertyMsg(T object, String propertyName, Class<?>... groups) {
        return ValidatorMsgUtil.toTList(validateProperty(object, propertyName, groups));
    }

    /**
     * 验证数据
     *
     * @param beanType     验证的类型
     * @param propertyName 属性名
     * @param value        属性值
     * @param groups       验证场景
     * @param <T>          类型
     * @return String for list
     */
    public <T> List<String> validateValueMsg(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        return ValidatorMsgUtil.toTList(validateValue(beanType, propertyName, value, groups));
    }

    /**
     * 验证数据并转换成目标对象
     *
     * @param jsonStr json string
     * @param tClass  目标对象类型
     * @param groups  场景
     * @param <T>     类型
     * @return 目标对象
     */
    public <T> T validate(String jsonStr, Class<T> tClass, Class<?>... groups) {
        T t = convert(jsonStr, tClass);
        Set<ConstraintViolation<T>> set = validate(t, groups);
        if (!set.isEmpty()) {
            throw new ConstraintViolationException(set);
        }
        return t;
    }

    /**
     * 验证数据并转换成目标对象
     *
     * @param jsonStr      json string
     * @param tClass       目标类型
     * @param propertyName 需要验证的属性名
     * @param groups       场景
     * @param <T>          类型
     * @return 目标对象
     */
    public <T> T validateProperty(String jsonStr, Class<T> tClass, String propertyName, Class<?>... groups) {
        T t = convert(jsonStr, tClass);
        Set<ConstraintViolation<T>> set = validateProperty(t, propertyName, groups);
        if (!set.isEmpty()) {
            throw new ConstraintViolationException(set);
        }
        return t;
    }


    /**
     * 获取验证器
     *
     * @return Validator
     */
    public Validator getValidator() {
        return validatorFactory.getValidator();
    }
}
