// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.ResourceEntity;
import cs.ohms.subsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, String>, JpaSpecificationExecutor<ResourceEntity> {
    Optional<ResourceEntity> findByIdAndUser(String id, UserEntity user);

    Optional<ResourceEntity> findByIdAndSuffix(String id, String suffix);
}
