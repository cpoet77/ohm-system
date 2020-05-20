// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/14.
package cn.ohms.subsystem.utils.leafo2o;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * LeafField
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class LeafField {
    /**
     * 属性名
     */
    private Field field;

    /**
     * 目标属性
     */
    private Field targetField;

    /**
     * set方法
     */
    private Method setMethod;

    /**
     * get方法
     */
    private Method getMethod;

    /**
     * 目标set方法
     */
    private Method targetSetMethod;

    /**
     * 目标get方法
     */
    private Method targetGetMethod;

    /**
     * 使用get/set方法赋值
     */
    private boolean applyMethod;

    /**
     * 使用的转换器
     */
    private LeafConvertField<Object, Object> leafConvertField;

    /**
     * 使用的场景
     */
    private List<Class<?>> groups;

    public LeafField() {}

    public LeafField(Field field, Field targetField) {
        this.field = field;
        this.targetField = targetField;
    }

    public LeafField(Field field, Field targetField, Method setMethod, Method targetSetMethod) {
        this.field = field;
        this.targetField = targetField;
        this.setMethod = setMethod;
        this.targetSetMethod = targetSetMethod;
    }

    public LeafField(Field field, Field targetField, Method setMethod, Method getMethod, Method targetSetMethod
            , Method targetGetMethod) {
        this.field = field;
        this.targetField = targetField;
        this.setMethod = setMethod;
        this.getMethod = getMethod;
        this.targetSetMethod = targetSetMethod;
        this.targetGetMethod = targetGetMethod;
    }

    /**
     * Gets the value of field.
     *
     * @return field
     */
    public Field getField() {
        return field;
    }

    /**
     * Sets the field.
     *
     * @param field field
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * Gets the value of targetField.
     *
     * @return targetField
     */
    public Field getTargetField() {
        return targetField;
    }

    /**
     * Sets the targetField.
     *
     * @param targetField targetField
     */
    public void setTargetField(Field targetField) {
        this.targetField = targetField;
    }

    /**
     * Gets the value of setMethod.
     *
     * @return setMethod
     */
    public Method getSetMethod() {
        return setMethod;
    }

    /**
     * Sets the setMethod.
     *
     * @param setMethod setMethod
     */
    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }

    /**
     * Gets the value of getMethod.
     *
     * @return getMethod
     */
    public Method getGetMethod() {
        return getMethod;
    }

    /**
     * Sets the getMethod.
     *
     * @param getMethod getMethod
     */
    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }

    /**
     * Gets the value of targetSetMethod.
     *
     * @return targetSetMethod
     */
    public Method getTargetSetMethod() {
        return targetSetMethod;
    }

    /**
     * Sets the targetSetMethod.
     *
     * @param targetSetMethod targetSetMethod
     */
    public void setTargetSetMethod(Method targetSetMethod) {
        this.targetSetMethod = targetSetMethod;
    }

    /**
     * Gets the value of targetGetMethod.
     *
     * @return targetGetMethod
     */
    public Method getTargetGetMethod() {
        return targetGetMethod;
    }

    /**
     * Sets the targetGetMethod.
     *
     * @param targetGetMethod targetGetMethod
     */
    public void setTargetGetMethod(Method targetGetMethod) {
        this.targetGetMethod = targetGetMethod;
    }

    /**
     * Gets the value of applyMethod.
     *
     * @return applyMethod
     */
    public boolean isApplyMethod() {
        return applyMethod;
    }

    /**
     * Sets the applyMethod.
     *
     * @param applyMethod applyMethod
     */
    public void setApplyMethod(boolean applyMethod) {
        this.applyMethod = applyMethod;
    }

    /**
     * Gets the value of leafConvertField.
     *
     * @return leafConvertField
     */
    public LeafConvertField<Object, Object> getLeafConvertField() {
        return leafConvertField;
    }

    /**
     * Sets the leafConvertField.
     *
     * @param leafConvertField leafConvertField
     */
    public void setLeafConvertField(LeafConvertField<Object, Object> leafConvertField) {
        this.leafConvertField = leafConvertField;
    }

    /**
     * Gets the value of groups.
     *
     * @return groups
     */
    public List<Class<?>> getGroups() {
        return groups;
    }

    /**
     * Sets the groups.
     *
     * @param groups groups
     */
    public void setGroups(List<Class<?>> groups) {
        this.groups = groups;
    }
}
