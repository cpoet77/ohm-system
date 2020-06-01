package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.MajorEntity;
import cs.ohms.subsystem.viewobject.MajorVo;

import java.util.List;

/**
 * 2020/5/23 1:36
 *
 * @author LRC
 */
public interface MajorService {
    /**
     * 获取所有专业信息
     *
     * @return MajorEntity for list
     */
    List<MajorEntity> findAll();

    /**
     * 根据学院获取其下所有专业的信息
     *
     * @param collegeId 学院id
     * @return MajorVo for list
     */
    List<MajorVo> findAllVoByCollege(Integer collegeId);

    /**
     * 根据学院id分布获取专业列表
     *
     * @param collegeId 学院id
     * @param start     开始位置
     * @param length    数量
     * @return ResponseResult
     */
    ResponseResult getMajorByCollegeAndPage(Integer collegeId, int start, int length);

    /**
     * 保存专业信息
     *
     * @param major MajorEntity
     * @return true | false
     */
    boolean saveMajor(MajorEntity major);

    /**
     * 保存专业信息
     *
     * @param majorId   专业id,为null时是添加专业，否则是更新专业信息
     * @param majorName 专业名
     * @param collegeId 学院id
     * @return true|false
     */
    boolean saveMajor(Integer majorId, String majorName, Integer collegeId);

    /**
     * 删除专业信息
     *
     * @param majorId 专业id
     * @return true|false
     */
    boolean deleteMajor(Integer majorId);

    /**
     * 据name 查询major
     * <p>在缓存有效期内，查找cache - database</p>
     *
     * @param name 专业名
     * @return MajorEntity
     */
    MajorEntity findMajorHashCacheByName(String name);
}
