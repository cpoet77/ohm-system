package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.LoginRecordEntity;
import cs.ohms.subsystem.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author _Struggler
 */
@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecordEntity, String>, JpaSpecificationExecutor<LoginRecordEntity> {
    Page<LoginRecordEntity> findByUser(UserEntity user, Pageable pageable);
}
