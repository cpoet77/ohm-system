package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.CollegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author xuxi
 */
@Repository
public interface CollegeRepository extends JpaRepository<CollegeEntity, Integer>, JpaSpecificationExecutor<CollegeEntity> {
    CollegeEntity findByName(String name);
}
