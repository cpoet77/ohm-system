package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.LoginRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author _Struggler
 */
@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecordEntity, Long>, JpaSpecificationExecutor<LoginRecordEntity> {
    Page<LoginRecordEntity> findByUser_Id(Integer user_id, Pageable pageable);

    Page<LoginRecordEntity> findByUser_NameIsLike(String user_name, Pageable pageable);
}
