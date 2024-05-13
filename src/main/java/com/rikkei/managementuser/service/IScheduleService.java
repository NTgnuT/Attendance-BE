package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.exception.ScheduleException;
import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.dto.request.ScheduleRequest;
import com.rikkei.managementuser.model.dto.response.ScheduleResponse;
import com.rikkei.managementuser.model.entity.Schedule;

import java.sql.Date;
import java.util.List;

public interface IScheduleService {
    void save (ScheduleRequest scheduleRequest) throws ScheduleException;
    ScheduleResponse findById(Long scheduleId);

    List<ScheduleResponse> findAllSchedule();

    void editSchedule(ScheduleRequest scheduleRequest, Long id);

    void deleteSchedule(Long id) throws NoPermissionToDelete;

    ScheduleResponse findScheduleByTeacherAndClass(ScheduleRequest s);
}
