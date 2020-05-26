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
     * 分页获取学院列表
     *
     * @param start  起始位置
     * @param length 每页长度
     * @return ResponseResult
     */
    ResponseResult getCollegesByPage(int start, int length);

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
     * 保存学院信息
     *
     * @param id          学院id,为null时为添加
     * @param name        学院名
     * @param description 学院简介
     * @return true|false
     */
    boolean saveCollege(Integer id, String name, String description);

    /**
     * 删除学院信息
     * <p><b>所有数据库表大部分为级联操作，请谨慎处理！</b></p>
     *
     * @param id 学院id
     * @return true|false
     */
    boolean deleteCollege(Integer id);

    /**
     * 根据name查询college
     * <p>在缓存有效期内，查找cache - database</p>
     *
     * @param name 学院名
     * @return CollegeEntity
     */
    CollegeEntity findCollegeHasCacheByName(String name);
}
