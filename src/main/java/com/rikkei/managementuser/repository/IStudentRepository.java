package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.dto.request.StatisticRequest;
import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import com.rikkei.managementuser.model.entity.Student;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {
    boolean existsByPhoneNumber(String number);
    boolean existsByEmail(String email);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByPhoneNumber(String phone);
    @Query("SELECT s FROM Student s JOIN s.aClass c WHERE c.id = ?1")
    List<Student> findByAClass_Id(Long id);

    @Query (value = "SELECT stu.id AS student_id, stu.Name AS student_name, " +
            "SUM(CASE WHEN ad.attendance_detail_status = 'PRESENT' THEN 1 ELSE 0 END) AS present, " +
            "SUM(CASE WHEN ad.attendance_detail_status = 'ABSENCE_WITHOUT_PERMISSION' THEN 1 ELSE 0 END) AS absence_with_out_permission, " +
            "SUM(CASE WHEN ad.attendance_detail_status = 'ABSENCE_WITH_PERMISSION' THEN 1 ELSE 0 END) AS absence_with_permission " +
            "FROM student stu " +
            "LEFT JOIN attendancedetail ad ON stu.id = ad.student_id " +
            "LEFT JOIN attendance a ON a.id = ad.attendance_id " +
            "LEFT JOIN schedule s ON s.id = a.schedule_id " +
            "WHERE s.class_id = :classId AND s.module_id = :moduleId " +
            "GROUP BY stu.id, stu.Name", nativeQuery = true)
    List<Object[]> getAttendanceStatistics(Long classId, Long moduleId);

}
