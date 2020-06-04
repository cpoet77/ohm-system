// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/21.
package cs.ohms.subsystem.repository;

import cs.ohms.subsystem.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Student Repository
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String>, JpaSpecificationExecutor<StudentEntity> {
    long countByCollege_id(Integer collegeId);

    long countByMajor_id(Integer majorId);

    long countByClazz_id(Integer clzzId);

    @Query(value = "SELECT COUNT(*) count FROM ohms_view_student WHERE student_id IN (SELECT student_id FROM ohms_student_course_group WHERE course_group_id = :courseGroupId)", nativeQuery = true)
    long countByCourseGroup_Id(@Param("courseGroupId") Integer courseGroupId);

    Page<StudentEntity> findByCollege_Id(Integer collegeId, Pageable pageable);

    Page<StudentEntity> findByMajor_Id(Integer majorId, Pageable pageable);

    Page<StudentEntity> findByClazz_Id(Integer clazzId, Pageable pageable);

    @Query(value = "SELECT * FROM ohms_view_student WHERE student_id IN (SELECT student_id FROM ohms_student_course_group WHERE course_group_id = :courseGroupId)", nativeQuery = true)
    Page<StudentEntity> findByCourseGroupId(@Param("courseGroupId") Integer courseGroupId, Pageable pageable);

    @Query(value = "SELECT * FROM ohms_view_student WHERE student_id IN (SELECT student_id FROM ohms_student_course_group WHERE course_group_id = :courseGroupId AND student_id LIKE :studentId)", nativeQuery = true)
    Page<StudentEntity> findByCourseGroupIdAndStudentIdIsLike(@Param("courseGroupId") Integer courseGroupId, @Param("studentId") String studentId, Pageable pageable);

    @Query(value = "SELECT * FROM ohms_view_student WHERE student_id IN (SELECT student_id FROM ohms_student_course_group WHERE course_group_id = :courseGroupId) AND user_id IN (SELECT id FROM ohms_user WHERE real_name LIKE :realName)", nativeQuery = true)
    Page<StudentEntity> findByCourseGroupIdAndUserRealNameLike(@Param("courseGroupId") Integer courseGroupId, @Param("realName") String realName, Pageable pageable);

    @Query(value = "SELECT * FROM ohms_view_student WHERE student_id IN (SELECT student_id FROM ohms_student_course_group WHERE course_group_id = :courseGroupId) AND (user_id IN (SELECT id FROM ohms_user WHERE real_name LIKE :realName) AND student_id LIKE :studentId)", nativeQuery = true)
    Page<StudentEntity> findByCourseGroupIdAndUserRealNameLikeAndStudentIdIsLike(@Param("courseGroupId") Integer courseGroupId, @Param("studentId") String studentId, @Param("realName") String realName, Pageable pageable);

    @Query(value = "SELECT * FROM ohms_view_student WHERE student_id IN (SELECT student_id FROM ohms_student_course_group WHERE course_group_id = :courseGroupId) AND (user_id IN (SELECT id FROM ohms_user WHERE real_name LIKE :realName) OR student_id LIKE :studentId)", nativeQuery = true)
    Page<StudentEntity> findByCourseGroupIdAndUserRealNameLikeOrStudentIdIsLike(@Param("courseGroupId") Integer courseGroupId, @Param("studentId") String studentId, @Param("realName") String realName, Pageable pageable);
}
