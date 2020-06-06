package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.TeacherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 2020/5/23 23:59
 *
 * @author LRC
 */
@Repository
public interface CourseGroupRepository extends JpaRepository<CourseGroupEntity, Integer>, JpaSpecificationExecutor<CourseGroupEntity> {
    int countByTeacher_TeacherId(String teacherId);

    Page<CourseGroupEntity> findAllByTeacher_TeacherIdIsLike(String teacher_teacherId, Pageable pageable);

    Page<CourseGroupEntity> findAllByNameIsLike(String name, Pageable pageable);

    Page<CourseGroupEntity> findAllByNameIsLikeAndTeacher_TeacherIdIsLike(String name, String teacher_teacherId, Pageable pageable);

    Page<CourseGroupEntity> findAllByNameIsLikeOrTeacher_TeacherIdIsLike(String name, String teacher_teacherId, Pageable pageable);

    Page<CourseGroupEntity> findAllByTeacher(TeacherEntity teacher, Pageable pageable);

    Page<CourseGroupEntity> findAllByTeacherAndNameIsLike(TeacherEntity teacher, String name, Pageable pageable);
}
