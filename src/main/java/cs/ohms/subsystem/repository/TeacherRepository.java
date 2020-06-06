// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.TeacherEntity;
import cs.ohms.subsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, String>, JpaSpecificationExecutor<TeacherEntity> {
    TeacherEntity findByUser(UserEntity user);
}
