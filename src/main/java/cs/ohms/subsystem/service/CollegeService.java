// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.CollegeEntity;
import cs.ohms.subsystem.vo.CollegeVo;

import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface CollegeService {
    /**
     * 获取所有学院信息
     *
     * @return CollegeVo for list
     */
    List<CollegeVo> findAll();

    /**
     * 分页获取学院列表
     *
     * @param start  起始位置
     * @param length 每页长度
     * @return ResponseResult
     */
    ResponseResult getCollegesByPage(int start, int length);

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
     * 存在则返回，不存在则创建，失败返回null
     *
     * @param identity 是否是管理员
     * @param name    学院名
     * @return CollegeEntity|null
     */
    CollegeEntity addCollege(String identity, String name);
}
