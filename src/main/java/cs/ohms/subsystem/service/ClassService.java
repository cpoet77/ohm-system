package cs.ohms.subsystem.service;

import cs.ohms.subsystem.entity.ClassEntity;

/**
 * 2020/5/30 14:18
 *
 * @auther _Struggler
 */
public interface ClassService {
    /**
     * 据name 查询class
     * <p>在缓存有效期内，查找cache - database</p>
     *
     * @param name 班级名
     * @return ClassEntity
     */
    ClassEntity findClassHashCacheByName(String name);
}
