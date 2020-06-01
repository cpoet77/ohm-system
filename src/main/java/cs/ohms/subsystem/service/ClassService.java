package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.ClassEntity;

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
     * 保存班级信息
     *
     * @param classEntity ClassEntity
     * @return true|false
     */
    boolean saveClass(ClassEntity classEntity);

    /**
     * 保存班级信息, classId为null时为新增
     *
     * @param classId   班级id
     * @param className 班级名
     * @param majorId   专业id
     * @return true|false
     */
    boolean saveClass(Integer classId, String className, Integer majorId);

    /**
     * 据name 查询class
     * <p>在缓存有效期内，查找cache - database</p>
     *
     * @param name 班级名
     * @return ClassEntity
     */
    ClassEntity findClassHashCacheByName(String name);
}
