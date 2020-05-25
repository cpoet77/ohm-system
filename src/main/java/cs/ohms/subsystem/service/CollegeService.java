// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CollegeEntity;

import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface CollegeService {
    /**
     * 获取所有学院信息
     *
     * @return CollegeEntity for list
     */
    List<CollegeEntity> findAll();

    /**
     * 导入学院信息
     *
     * @param in InputStream
     * @return ResponseResult
     */
    ResponseResult importCollegeInfo(InputStream in);

    /**
     * 保存学院信息
     *
     * @param college CollegeEntity
     * @return true | false
     */
    boolean saveCollege(CollegeEntity college);

    /**
     * 根据name查询college
     * <p>在缓存有效期内，查找cache - database</p>
     *
     * @param name 学院名
     * @return CollegeEntity
     */
    CollegeEntity findCollegeHasCacheByName(String name);
}
