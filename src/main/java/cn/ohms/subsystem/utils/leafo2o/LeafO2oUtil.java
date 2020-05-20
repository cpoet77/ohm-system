// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/14.
package cn.ohms.subsystem.utils.leafo2o;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * POJO转换工具
 *
 * <p>效率不高，但是可以解决手动调用pojo的get/set方法的繁琐。没有场景要求，直接拷贝对象的话推荐使用BeanUtils.copyProperties</p>
 * <p>显然BeanUtils.copyProperties更加的高效，但是目前项目暂时不需要考虑效率问题。</p>
 * <p><b>建议：针对一种转换准备一个LeafO2oUtil，然后使用容器将其存储起来，如此可以省去读取信息的时间。</b></p>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see org.springframework.beans.BeanUtils
 */
public class LeafO2oUtil<S, T> {
    private static final Logger log = LoggerFactory.getLogger(LeafO2oUtil.class);
    /**
     * Has annotated source
     */
    private Class<S> source;

    /**
     * No annotated target
     */
    private Class<T> target;

    /**
     * Storage attribute related container
     */
    private LeafFieldSet fieldSet;


    /**
     * Class object conversion, this method is not empty, will not parse attributes
     */
    private LeafConvertCase<S, T> convertCase;

    /**
     * Switch scene
     */
    private List<Class<?>> groups;

    /**
     * 构造器
     *
     * @param source 源class对象
     * @param target 目标class对象
     */
    public LeafO2oUtil(@NotNull Class<S> source, @NotNull Class<T> target) {
        this.source = source;
        this.target = target;
        this.fieldSet = new LeafFieldSet();
        this.initiate();
    }

    /**
     * 初始化信息
     *
     * <p>加载source和target中的field或者method信息。仅读取加上注解的field</p>
     */
    private void initiate() {
        LeafCase leafCase = source.getDeclaredAnnotation(LeafCase.class);
        try {
            if (leafCase != null && leafCase.groups().length > 0) {
                convertCase = (LeafConvertCase<S, T>) leafCase.converter().newInstance();
                this.groups = Arrays.asList(leafCase.groups());
            } else {
                this.parseField();
            }
        } catch (LeafConvertException | IllegalAccessException | InstantiationException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 解析获取fields
     *
     * @throws LeafConvertException LeafConvertException
     */
    private void parseField() throws LeafConvertException {
        Field[] fields = source.getDeclaredFields();
        if (fields.length == 0) {
            throw new LeafConvertException("This is a class with no specific attributes, somehow");
        }
        for (Field field : fields) {
            LeafMapping leafMapping = field.getAnnotation(LeafMapping.class);
            if (null != leafMapping) {
                putFieldInfo(leafMapping, field);
            }
        }
    }

    /**
     * 读取field信息
     *
     * @param leafMapping 属性上的LeafMapping注解
     * @param field       属性类
     * @throws LeafConvertException LeafConvertException
     */
    private void putFieldInfo(@NotNull LeafMapping leafMapping, @NotNull Field field) throws LeafConvertException {
        String fieldName = field.getName();
        String targetFieldName = leafMapping.targetName().isEmpty() ? fieldName : leafMapping.targetName();
        try {
            Field targetField = target.getDeclaredField(targetFieldName);
            if (targetField == null) {
                throw new LeafConvertException("No corresponding field found in the target,source field name : " + fieldName);
            }
            LeafField leafField = new LeafField(field, targetField);
            leafField.setApplyMethod(leafMapping.method());
            leafField.setGroups(Arrays.asList(leafMapping.groups()));
            leafField.setLeafConvertField(leafMapping.converter().newInstance());
            if (leafMapping.method()) {
                putFieldAndMethodInfo(leafField, leafMapping, field, targetField);
            } else {
                fieldSet.add(leafField);
            }
        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
            throw new LeafConvertException(e);
        }
    }

    /**
     * 在需要使用get/set方法的情况下，使用本方法读取method信息
     *
     * @param leafField   LeafField
     * @param leafMapping LeafMapping
     * @param field       Field
     * @param targetField Field
     * @throws LeafConvertException LeafConvertException
     */
    private void putFieldAndMethodInfo(@NotNull LeafField leafField, @NotNull LeafMapping leafMapping
            , @NotNull Field field, @NotNull Field targetField) throws LeafConvertException {
        String setMethodName = leafMapping.set().isEmpty() ? toFieldSetMethodName(field.getName()) : leafMapping.set();
        String getMethodName = leafMapping.get().isEmpty() ? toFieldGetMethodName(field.getName()) : leafMapping.get();
        String targetSetMethodName = leafMapping.targetSet().isEmpty() ? toFieldSetMethodName(targetField.getName())
                : leafMapping.targetSet();
        String targetGetMethodName = leafMapping.targetGet().isEmpty() ? toFieldGetMethodName(targetField.getName())
                : leafMapping.targetGet();
        leafField.setSetMethod(getMethod(source, setMethodName, field.getType()));
        leafField.setGetMethod(getMethod(source, getMethodName));
        leafField.setTargetSetMethod(getMethod(target, targetSetMethodName, targetField.getType()));
        leafField.setTargetGetMethod(getMethod(target, targetGetMethodName));
        fieldSet.add(leafField);
    }

    /**
     * 根据方法名读取方法
     *
     * @param clazz         Class对象
     * @param methodName    方法名
     * @param parameterType 方法参数列表
     * @return Method实例
     * @throws LeafConvertException LeafConvertException
     */
    @NotNull
    private Method getMethod(@NotNull Class<?> clazz, String methodName
            , Class<?>... parameterType) throws LeafConvertException {
        try {
            Method method = clazz.getMethod(methodName, parameterType);
            if (method == null) {
                throw new LeafConvertException("No target method found, method name:" + methodName);
            }
            return method;
        } catch (NoSuchMethodException e) {
            throw new LeafConvertException(e);
        }
    }

    /**
     * 生成field的set方法名
     *
     * @param fieldName field名称
     * @return set方法名
     */
    @NotNull
    private String toFieldSetMethodName(String fieldName) {
        return toMethodName("set", fieldName);
    }

    /**
     * 生存get方法名
     *
     * @param fieldName 属性名
     * @return get方法名
     */
    @NotNull
    private String toFieldGetMethodName(String fieldName) {
        return toMethodName("get", fieldName);
    }

    /**
     * 组合方法名
     *
     * @param fix       名称前缀
     * @param fieldName 名称
     * @return 生成的方法名
     */
    @NotNull
    private String toMethodName(String fix, @NotNull String fieldName) {
        char firstChar = fieldName.charAt(0);
        StringBuilder builder = new StringBuilder(fix);
        if (firstChar >= 'a' && firstChar <= 'z') {
            builder.append((char) (firstChar - 32));
        } else {
            builder.append(firstChar);
        }
        return builder.append(fieldName.substring(1)).toString();
    }

    /**
     * 检查场景是否配合
     *
     * @param groups  源场景
     * @param tGroups 目标场景
     * @return true|false
     */
    private boolean inGroup(@NotNull List<Class<?>> groups, Class<?>[] tGroups) {
        if (groups.isEmpty() && tGroups.length == 0) {
            return true;
        }
        for (Class<?> tClass : tGroups) {
            if (groups.contains(tClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 正向转换
     *
     * @param source source
     * @param target target
     * @param groups 转换场景
     * @return 结果对象
     */
    public T convert(S source, T target, Class<?>... groups) {
        if (convertCase != null) {
            if (inGroup(this.groups, groups)) {
                return convertCase.convert(source, target);
            }
            return null;
        }
        fieldSet.forEach(leafField -> {
            if (inGroup(leafField.getGroups(), groups)) {
                try {
                    if (leafField.isApplyMethod()) {
                        Object value = leafField.getLeafConvertField().convert(leafField.getGetMethod().invoke(source)
                                , leafField.getTargetGetMethod().invoke(target));
                        leafField.getTargetSetMethod().invoke(target, value);
                    } else {
                        Field field = leafField.getField();
                        Field targetField = leafField.getTargetField();
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        if (!targetField.isAccessible()) {
                            targetField.setAccessible(true);
                        }
                        Object value = leafField.getLeafConvertField().convert(field.get(source), targetField.get(target));
                        targetField.set(target, value);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error(e.getMessage(), new LeafConvertException(e));
                }
            }
        });
        return target;
    }

    /**
     * 正向转换
     * <p>自动实例化一个对象</p>
     *
     * @param source 源对象
     * @param groups 验证场景
     * @return 目标对象
     * @throws LeafConvertException LeafConvertException
     */
    public T convert(S source, Class<?>... groups) throws LeafConvertException {
        try {
            return convert(source, target.newInstance(), groups);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new LeafConvertException(e);
        }
    }

    /**
     * 反向转换
     *
     * @param source source
     * @param target target
     * @param groups 转换场景
     * @return 目标对象
     */
    public S reversal(S source, T target, Class<?>... groups) {
        if (convertCase != null) {
            if (inGroup(this.groups, groups)) {
                return convertCase.reversal(source, target);
            }
            return null;
        }
        fieldSet.forEach(leafField -> {
            if (inGroup(leafField.getGroups(), groups)) {
                try {
                    if (leafField.isApplyMethod()) {
                        Object value = leafField.getLeafConvertField().reversal(leafField.getGetMethod().invoke(source)
                                , leafField.getTargetGetMethod().invoke(target));
                        leafField.getSetMethod().invoke(source, value);
                    } else {
                        Field field = leafField.getField();
                        Field targetField = leafField.getTargetField();
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        if (!targetField.isAccessible()) {
                            targetField.setAccessible(true);
                        }
                        Object value = leafField.getLeafConvertField().reversal(field.get(source), targetField.get(target));
                        field.set(source, value);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error(e.getMessage(), new LeafConvertException(e));
                }
            }
        });
        return source;
    }

    /**
     * 反向转换
     * <p>自动实例化一个结果对象</p>
     *
     * @param target target
     * @param groups 转换场景
     * @return 结果对象
     * @throws LeafConvertException LeafConvertException
     */
    public S reversal(T target, Class<?>... groups) throws LeafConvertException {
        try {
            return reversal(source.newInstance(), target, groups);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new LeafConvertException(e);
        }
    }
}
