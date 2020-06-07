// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/07.
package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.HomeworkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface HomeworkRepository extends JpaRepository<HomeworkEntity, Integer>, JpaSpecificationExecutor<HomeworkEntity> {
    Optional<HomeworkEntity> findByCourseGroup_IdAndId(Integer courseGroup_id, Integer id);

    Page<HomeworkEntity> findByCourseGroup(CourseGroupEntity courseGroup, Pageable pageable);

    Page<HomeworkEntity> findByCourseGroupAndTitleIsLike(CourseGroupEntity courseGroup, String title, Pageable pageable);

    Page<HomeworkEntity> findByCourseGroupAndStateIsTrueAndEndTimeIsAfter(CourseGroupEntity courseGroup, LocalDateTime endTime, Pageable pageable);

    Page<HomeworkEntity> findByCourseGroupAndStateIsFalseOrEndTimeIsBefore(CourseGroupEntity courseGroup, LocalDateTime endTime, Pageable pageable);

    Page<HomeworkEntity> findByCourseGroupAndTitleIsLikeAndStateIsTrueAndEndTimeIsAfter(CourseGroupEntity courseGroup, String title, LocalDateTime endTime, Pageable pageable);

    Page<HomeworkEntity> findByCourseGroupAndTitleIsLikeAndStateIsFalseOrEndTimeIsBefore(CourseGroupEntity courseGroup, String title, LocalDateTime endTime, Pageable pageable);
}
