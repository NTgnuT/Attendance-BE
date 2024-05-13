package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllBySchedule_Id (Long scheduleId);
}
