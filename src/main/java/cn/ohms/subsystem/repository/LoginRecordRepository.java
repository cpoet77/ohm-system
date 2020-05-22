package cn.ohms.subsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @auther _Struggler
 * 2020/5/22 11:04
 */
public interface LoginRecordRepository extends JpaRepository<LoginRecordRepository,String>, JpaSpecificationExecutor<LoginRecordRepository> {

}
