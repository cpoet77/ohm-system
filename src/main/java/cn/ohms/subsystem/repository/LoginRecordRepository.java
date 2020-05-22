package cn.ohms.subsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author _Struggler
 */
@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecordRepository, String>, JpaSpecificationExecutor<LoginRecordRepository> {

}
