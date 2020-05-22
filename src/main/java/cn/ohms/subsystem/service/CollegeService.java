// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cn.ohms.subsystem.service;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.entity.CollegeEntity;

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
     * 保护学院信息
     *
     * @param college CollegeEntity
     * @return true | false
     */
    boolean saveCollege(CollegeEntity college);
}
