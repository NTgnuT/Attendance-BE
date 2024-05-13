package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.CourseExistException;
import com.rikkei.managementuser.model.dto.request.AttendanceDetailRequest;
import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.entity.AttendanceDetail;

import java.util.List;

public interface IAttendanceDetailService {
//    void updateAttendanceDetails(AttendanceDetailRequest attDetail) throws CourseExistException;
    List<AttendanceDetail> findAttendanceDetailByAttendance(Long id);
    void updateAttendanceDetails(List<AttendanceDetailRequest> attDetail) throws CourseExistException;

}
