package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.dto.response.AttendanceResponse;

import java.util.List;

public interface IAttendanceService {
    AttendanceResponse findById(Long attendanceId);
    List<AttendanceResponse> findAllAttendance();
    List<AttendanceResponse> findAttendance (AttendanceRequest attendanceRequest);
}
