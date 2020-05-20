// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/6.
package cn.ohms.subsystem.service;

import java.util.Set;

/**
 * appService
 * <p><b>为系统提供初始化操作</b></p>
 * <p><b>当找不到查询的属性时，将进行数据库查询并保存起来，便于后续访问</b></p>
 * <p>提供reload方法，为属性被更新后提供重新加载的入口</p>
 * <p>率先读入的有数据表system的内容，SystemEntity.properties的部分属性</p>
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface AppService {
    /**
     * 获取系统名称
     * @return system name
     */
    String getName();

    /**
     * 获取当前系统的版本
     * @return system version
     */
    String getVersion();

    /**
     * 获取部署的域名
     * <p>http(s)://...</p>
     * @return entity
     */
    String getDomain();

    /**
     * get属性
     * @param name 需要获取的属性
     * @return 属性|null
     */
    Object get(String name);

    /**
     * 获取属性值
     * @param name 名称
     * @return string value
     */
    String getAsString(String name);

    /**
     * 获取属性值
     * @param name 名称
     * @return int value
     */
    int getAsInt(String name);

    /**
     * 获取属性值
     * @param name 名称
     * @return long value
     */
    long getAsLong(String name);

    /**
     * 获取属性值
     * @param name 名称
     * @return float value
     */
    float getAsFloat(String name);

    /**
     * 查询当前属性是否已被设定
     * @param name 属性名
     * @return true|false
     */
    boolean contain(String name);

    /**
     * 返回当前已经加载的所有属性名
     * @return Property Names for set
     */
    Set<String> propertyNames();

    /**
     * 重新加载所有属性
     */
    void reload();
}
