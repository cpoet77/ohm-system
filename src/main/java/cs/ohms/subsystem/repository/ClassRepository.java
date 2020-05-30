package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 2020/5/30 14:39
 *
 * @auther _Struggler
 */
@Repository
public interface ClassRepository extends JpaRepository<ClassEntity,Integer>, JpaSpecificationExecutor<ClassEntity>{
    ClassEntity findByName(String name);
}
