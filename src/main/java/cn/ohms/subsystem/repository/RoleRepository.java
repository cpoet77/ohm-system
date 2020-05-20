// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/12.
package cn.ohms.subsystem.repository;

import cs.ohmsubsystem.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Byte>, JpaSpecificationExecutor<RoleEntity> {
    RoleEntity findByName(String roleName);
}
