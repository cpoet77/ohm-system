package cn.ohms.subsystem.repository;

import cn.ohms.subsystem.entity.CourseGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * CourseGroup  Repository
 * @author LRC
 * @date 2020/5/21 18:42
 */

@Repository
public interface CourseGroupRepository extends JpaRepository<CourseGroupEntity,String>,
        JpaSpecificationExecutor<CourseGroupEntity> {
}
