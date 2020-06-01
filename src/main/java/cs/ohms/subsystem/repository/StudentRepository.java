// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/21.
package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Student Repository
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String>, JpaSpecificationExecutor<StudentEntity> {
    long countByCollege_id(Integer collegeId);

    long countByMajor_id(Integer majorId);

    long countByClazz_id(Integer clzzId);
}
