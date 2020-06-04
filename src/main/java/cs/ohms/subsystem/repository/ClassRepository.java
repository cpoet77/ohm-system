package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 2020/5/30 14:39
 *
 * @author _Struggler
 */
@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer>, JpaSpecificationExecutor<ClassEntity> {
    boolean existsByName(String name);

    ClassEntity findByName(String name);

    long countByMajor_Id(Integer majorId);

    long countByCollege_Id(Integer collegeId);

    Page<ClassEntity> findAllByMajor_Id(Integer majorId, Pageable pageable);

    Page<ClassEntity> findAllByCollege_Id(Integer collegeId, Pageable pageable);

    List<ClassEntity> findAllByMajor_Id(Integer majorId);
}
