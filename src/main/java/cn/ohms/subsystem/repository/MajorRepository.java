package cn.ohms.subsystem.repository;

import cn.ohms.subsystem.entity.MajorEntity;
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
}
