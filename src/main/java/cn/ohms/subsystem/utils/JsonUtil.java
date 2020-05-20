// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/3/26.
package cn.ohms.subsystem.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Json操作简化工具
 * 继承于jackson的ObjectMapper
 * @see ObjectMapper
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class JsonUtil extends ObjectMapper{
    private final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * json字符串转换成指定类型
     * @param jsonStr jsonParse
     * @param valueType 目标类型Class对象
     * @param <T> 目标类型
     * @return 转换后的结果，失败为null
     */
    public <T> T read(String jsonStr, Class<T> valueType){
        try {
            return readValue(jsonStr, valueType);
        }catch (JsonProcessingException e){
            log.warn("JSON parsing failed!", e);
        }
        return null;
    }

    /**
     * json字符串转换成指定类型
     * @param jsonStr jsonParse
     * @param valueTypeRef TypeReference
     * @param <T> 目标类型
     * @return 转换后的结果，失败为null
     */
    public <T> T read(String jsonStr, TypeReference<T> valueTypeRef){
        try {
            return readValue(jsonStr, valueTypeRef);
        }catch (JsonProcessingException e){
            log.warn("JSON parsing failed!", e);
        }
        return null;
    }

    /**
     * 对象转换成json
     * @param o 需要转换的对象
     * @return json串，失败为null
     */
    public String write(Object o){
        try {
            return writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.warn("json operation failed!", e);
        }
        return null;
    }

    /**
     * 去json化，反序列化
     * @param jsonStr json串
     * @param valueType 目标类型的Class对象
     * @param <T> 泛型，目标类型
     * @return 指定T类型的对象，失败为null
     */
    public static <T> T decode(String jsonStr, Class<T> valueType){
        return new JsonUtil().read(jsonStr, valueType);
    }

    /**
     * 去json化，反序列化
     * @param jsonStr json串
     * @param valueTypeRef TypeReference
     * @param <T> 类型
     * @return 指定T类型的对象，失败为null
     */
    public static <T> T decode(String jsonStr,TypeReference<T> valueTypeRef){
        return new JsonUtil().read(jsonStr, valueTypeRef);
    }

    /**
     * 转换成json
     * @param o 需要转换的对象
     * @return json序列化字符串 ，失败为null
     */
    public static String encode(Object o){
        return new JsonUtil().write(o);
    }

    /**
     * 获取TypeReference
     * 用于Json反序列化结果值的类型
     * @return HashMap
     */
    @NotNull
    @Contract(" -> new")
    public static TypeReference<Map<String, String>> mapTypeReferenceStr(){
        return new TypeReference<Map<String, String> >(){};
    }

    /**
     * 获取TypeReference
     * 用于Json反序列化结果值的类型
     * @return HashMap
     */
    @NotNull
    @Contract(" -> new")
    public static TypeReference<Map<String, Object>> mapTypeReferenceObj(){
        return new TypeReference<Map<String, Object> >() {};
    }

    /**
     * 获取TypeReference
     * 用于Json反序列化结果值的类型
     * @return List.String
     */
    @NotNull
    @Contract(" -> new")
    public static TypeReference<List<String>> listTypeReferenceStr(){
        return new TypeReference<List<String> >() {};
    }

    /**
     * 获取TypeReference
     * 用于Json反序列化结果值的类型
     * @return List.Object
     */
    @NotNull
    @Contract(" -> new")
    public static TypeReference<List<Object>> listTypeReferenceObj(){
        return new TypeReference<List<Object> >() {};
    }
}
