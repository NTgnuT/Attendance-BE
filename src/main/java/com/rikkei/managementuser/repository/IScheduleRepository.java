package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.Class;
import com.rikkei.managementuser.model.entity.ModuleCourse;
import com.rikkei.managementuser.model.entity.Schedule;
import com.rikkei.managementuser.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IScheduleRepository extends JpaRepository<Schedule, Long> {
//    List<Schedule> findAllByTimeStartContainingIgnoreCase(Date time);

//    @Query(value = """
//    select * from schedule s inner join attendance a on s.scheduleId = a.schedule_id
//    left join attendancedetail ad on a.id = ad.attendance_id
//""", nativeQuery = true)
@Query("SELECT s FROM Schedule s WHERE s.teacher.id = :teacherId AND s.aClass.id = :classId AND s.moduleCourse.id = :moduleId")
Schedule findSchedule(Long teacherId, Long classId, Long moduleId);

    @Query("SELECT s FROM Schedule s WHERE s.aClass.id = :classId AND s.moduleCourse.id = :moduleId")
    Schedule findScheduleByClassIdAndModuleId(Long classId, Long moduleId);
}
