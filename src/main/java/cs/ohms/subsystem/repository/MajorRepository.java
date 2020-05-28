package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.MajorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Major Repository
 *
 * @author shc
 */
@Repository
public interface MajorRepository extends JpaRepository<MajorEntity, Integer>, JpaSpecificationExecutor<MajorEntity> {
    MajorEntity findByName(String name);

    long countByCollege_Id(Integer collegeId);

    Page<MajorEntity> findByCollege_Id(Integer collegeId, Pageable pageable);
}
