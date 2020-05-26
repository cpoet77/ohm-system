package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.MajorEntity;

import java.io.InputStream;
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
     * 导入专业信息
     *
     * @param in InputStream
     * @return ResponseResult
     */
    ResponseResult importMajorInfo(InputStream in);

    /**
     * 保存专业信息
     *
     * @param major MajorEntity
     * @return true | false
     */
    boolean saveMajor(MajorEntity major);

    /**
     * 据name 查询major
     * <p>在缓存有效期内，查找cache - database</p>
     *
     * @param name 专业名
     * @return MajorEntity
     */
    MajorEntity findMajorHashCacheByName(String name);
}
