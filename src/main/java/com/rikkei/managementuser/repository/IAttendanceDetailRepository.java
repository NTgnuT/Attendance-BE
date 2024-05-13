package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.dto.response.StatisticResponse;
import com.rikkei.managementuser.model.entity.AttendanceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAttendanceDetailRepository extends JpaRepository<AttendanceDetail, Long> {
    List<AttendanceDetail> findAttendanceDetailByAttendance_Id (Long id);
    AttendanceDetail findByStudentIdAndAttendanceId(Long studentId, Long attendanceId);

}
