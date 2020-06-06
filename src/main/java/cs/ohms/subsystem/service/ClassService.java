package cs.ohms.subsystem.service;

import cs.ohms.subsystem.common.ResponseResult;
import cs.ohms.subsystem.entity.ClassEntity;
import cs.ohms.subsystem.viewobject.ClassVo;

import java.util.List;

/**
 * 2020/5/30 14:18
 *
 * @author _Struggler
 */
public interface ClassService {
    /**
     * 分页获取班级信息
     *
     * @param collegeId 学院id
     * @param majorId   专业id
     * @param start     开始位置
     * @param length    长度
     * @return ResponseResult
     */
    ResponseResult getClassByCollegeAndMajorAndPage(Integer collegeId, Integer majorId, Integer start, Integer length);

    /**
     * 根据专业获取班级信息
     *
     * @param majorId 专业id
     * @return classVo for list
     */
    List<ClassVo> getAllClassByMajor(Integer majorId);

    /**
     * 新增班级
     *
     * @param identity     是否是管理员
     * @param collegeName 学院名
     * @param majorName   专业名
     * @param className   班级名
     * @return null|ClassEntity
     */
    ClassEntity addClass(String identity, String collegeName, String majorName, String className);

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
     * 根据id删除班级信息
     *
     * @param classId 班级id
     * @return true|false
     */
    boolean deleteClass(Integer classId);
}
