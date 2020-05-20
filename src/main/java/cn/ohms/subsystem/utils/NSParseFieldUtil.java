// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/7.
package cn.ohms.subsystem.utils;

import cs.ohmsubsystem.exception.NSParseFieldException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


/**
 * 用于接收前端参数，并解析获取对象
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class NSParseFieldUtil {
    // 目标对象
    MultiValueMap<String, String> map;

    public NSParseFieldUtil(@NotNull MultiValueMap<String, String> map) {
        this.map = map;
    }

    /**
     * Gets the value of map.
     *
     * @return map
     */
    public MultiValueMap<String, String> getMap() {
        return map;
    }

    /**
     * Sets the map.
     *
     * @param map map
     */
    public void setMap(MultiValueMap<String, String> map) {
        this.map = map;
    }

    /**
     * 从map解析目标对象
     * @param clazz Class
     * @param <T> 对象类型
     * @param groups 验证场景
     * @return 实例对象
     */
    public <T> T got(@NotNull Class<T> clazz, Class<?> ... groups){
        T object;
        try {
            Field[] fields = clazz.getDeclaredFields();
            object = clazz.newInstance();
            for (Field field : fields) {
                NSParseField nsParseField = field.getAnnotation(NSParseField.class);
                if(nsParseField != null && verificationScenario(groups, nsParseField.groups())){
                    /* 设定可访问性 */
                    if(!field.isAccessible()){
                        field.setAccessible(true);
                    }
                    field.set(object, getValue(field, nsParseField));
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new NSParseFieldException(e);
        }
        return object;
    }

    /**
     * 通过类名获取对象
     * @param className 类名
     * @param groups 验证场景
     * @return Object
     */
    public Object got(String className, Class<?> ... groups){
        try {
            return got(Class.forName(className), groups);
        } catch (ClassNotFoundException e) {
            throw new NSParseFieldException(e);
        }
    }

    /**
     * 处理场景
     * @param groups 需要验证的场景
     * @param filedGroups 类属性上设定的场景
     * @return true|false
     */
    private boolean verificationScenario(@NotNull Class<?>[] groups, Class<?>[] filedGroups){
        if(groups.length == 0){
            return (filedGroups.length == 0);
        }
        List<Class<?> > list = Arrays.asList(groups);
        for(Class<?> clazz : filedGroups){
            if(list.contains(clazz)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取属性值
     * <ul>
     *     <li>直接获取</li>
     *     <li>不区分大小写获取</li>
     *     <li>转换驼峰获取</li>
     * </ul>
     * <p>具体参照注解</p>
     * @param field File
     * @param nsParseField NSParseField
     * @return Object
     */
    @Nullable
    private Object getValue(Field field, @NotNull NSParseField nsParseField){
        /* 获取属性名 */
        String fieldName = NStringUtil.isEmpty(nsParseField.name())? field.getName() : nsParseField.name();
        if(map.containsKey(fieldName)){     /* 直接获取 */
            return castForString2Object(field.getType(), map.getFirst(fieldName));
        }
        if(!nsParseField.matchCase()){ /* 不区分大小写 */
            for (String name : map.keySet()) {
                if(fieldName.equalsIgnoreCase(name)){
                    return castForString2Object(field.getType(), map.getFirst(name));
                }
            }
        }
        String humpName = conversionHump(fieldName);
        if(nsParseField.conversion() && map.containsKey(humpName)){   /* 转换驼峰 */
            return castForString2Object(field.getType(), map.getFirst(humpName));
        }
        if(!"@%#%".equals(nsParseField.value())){   /* 使用默认值 */
            return castForString2Object(field.getType(), nsParseField.value());
        }
        return null;
    }

    /**
     * 驼峰命名的简单转换
     * <p>userName to user_name</p>
     * @param name String
     * @return result string
     */
    @NotNull
    private String conversionHump(@NotNull String name){
        char[] chars = name.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char aChar : chars) {
            if (aChar >= 'A' && aChar <= 'Z') {
                builder.append(NStringUtil.joint("_{}", aChar).toLowerCase());
            } else {
                builder.append(aChar);
            }
        }
        return builder.toString();
    }

    /**
     * string转换为常用类型
     * <p><b>需要注意LocalDate和LocalDateTime的格式</b></p>
     * @param type class
     * @param str string
     * @return Object
     */
    private Object castForString2Object(Class<?> type, String str){
        if(str == null){ return null; }
        try {
            if(String.class.equals(type)){
                return str;
            }
            else if (Integer.class.equals(type)) {
                return Integer.valueOf(str);
            } else if (Boolean.class.equals(type)) {
                return Boolean.valueOf(str);
            } else if (LocalDateTime.class.equals(type)) {
                return LocalDateTime.parse(str, DateTimeFormatter.ISO_DATE_TIME);
            } else if (LocalDate.class.equals(type)) {
                return LocalDate.parse(str, DateTimeFormatter.ISO_DATE);
            } else if (Long.class.equals(type)) {
                return Long.parseLong(str);
            } else if (Float.class.equals(type)) {
                return Float.parseFloat(str);
            } else if (Character.class.equals(type)) {
                return str.charAt(0);
            } else if (Double.class.equals(type)) {
                return Double.valueOf(str);
            } else if (Byte.class.equals(type)) {
                return Byte.valueOf(str);
            } else if (Short.class.equals(type)) {
                return Short.valueOf(str);
            }
            return type.cast(str);
        } catch (Exception e) {
            throw new NSParseFieldException(e);
        }
    }
}
