package cn.ohms.subsystem.repository;

import cn.ohms.subsystem.entity.CollegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author xuxi
 * @date 2020-05-21 20:31
 **/

@Repository
public interface CollegeRepository extends JpaRepository <CollegeEntity,String>, JpaSpecificationExecutor<CollegeEntity> {
}
