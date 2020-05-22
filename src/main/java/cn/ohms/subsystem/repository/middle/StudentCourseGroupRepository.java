// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cn.ohms.subsystem.repository.middle;

import cn.ohms.subsystem.entity.middle.StudentCourseGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface StudentCourseGroupRepository extends JpaRepository<StudentCourseGroupEntity, StudentCourseGroupEntity.Key>
        , JpaSpecificationExecutor<StudentCourseGroupEntity> {
}
