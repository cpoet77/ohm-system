// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/12.
package cn.ohms.subsystem.repository;

import cs.ohmsubsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer >, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByIdAndEmail(Integer id, String email);

    UserEntity findByEmail(String email);

    boolean existsByName(String name);

    UserEntity findByIdAndName(Integer id, String name);
}
