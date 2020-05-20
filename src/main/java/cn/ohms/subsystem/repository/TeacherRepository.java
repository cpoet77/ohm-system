// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/13.
package cn.ohms.subsystem.repository;

import cs.ohmsubsystem.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * TeacherRepository
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer>, JpaSpecificationExecutor<TeacherEntity> {
}
