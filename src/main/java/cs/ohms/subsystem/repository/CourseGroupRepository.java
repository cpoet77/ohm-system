package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.CourseGroupEntity;
import cs.ohms.subsystem.entity.StudentEntity;
import cs.ohms.subsystem.entity.TeacherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT * FROM ohms_course_group WHERE id IN (SELECT course_group_id FROM ohms_student_course_group WHERE student_id = :#{#student.studentId})", nativeQuery = true)
    Page<CourseGroupEntity> findAllByStudent(@Param("student") StudentEntity student, Pageable pageable);

    @Query(value = "SELECT * FROM ohms_course_group WHERE id IN (SELECT course_group_id FROM ohms_student_course_group WHERE student_id = :#{#student.studentId}) AND name LIKE :name", nativeQuery = true)
    Page<CourseGroupEntity> findAllByStudentAndNameIsLike(@Param("student") StudentEntity student, @Param("name") String name, Pageable pageable);
}
