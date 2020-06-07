// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.HomeworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface HomeworkRepository extends JpaRepository<HomeworkEntity, Integer>, JpaSpecificationExecutor<HomeworkEntity> {
}
