package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.ClassEntity;

import java.io.InputStream;

/**
 * 2020/5/30 14:18
 *
 * @author _Struggler
 */
public interface ClassService {
    /**
     * 分页获取班级信息
     *
     * @param majorId 专业id
     * @param start   开始位置
     * @param length  长度
     * @return ResponseResult
     */
    ResponseResult getClassByMajorAndPage(Integer majorId, Integer start, Integer length);

    /**
     * 导入班级信息
     *
     * @param in InputStream
     * @return ResponseResult
     */
    ResponseResult importMajorInfo(InputStream in);

    /**
     * 保存班级信息
     *
     * @param classEntity ClassEntity
     * @return true|false
     */
    boolean saveClass(ClassEntity classEntity);

    /**
     * 据name 查询class
     * <p>在缓存有效期内，查找cache - database</p>
     *
     * @param name 班级名
     * @return ClassEntity
     */
    ClassEntity findClassHashCacheByName(String name);
}
