// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/13.
package cn.ohms.subsystem.repository;

import cs.ohmsubsystem.entity.LoginRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * LoginRecordRepository
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecordEntity, Long>, JpaSpecificationExecutor<LoginRecordEntity> {
}
