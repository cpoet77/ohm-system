package cn.ohms.subsystem.service;

import cn.ohms.subsystem.entity.MajorEntity;

import java.util.List;

/**
 * 2020/5/23 1:36
 *
 * @author LRC
 */
public interface MajorService {
    List<MajorEntity> findAll();
}
