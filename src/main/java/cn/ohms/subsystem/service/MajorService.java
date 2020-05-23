package cn.ohms.subsystem.service;

import cn.ohms.subsystem.common.ResponseResult;
import cn.ohms.subsystem.entity.MajorEntity;

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
}
