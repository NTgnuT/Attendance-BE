package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.CourseExistException;
import com.rikkei.managementuser.model.dto.request.AttendanceDetailRequest;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.service.IAttendanceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user-management/api/attendance-detail")
@RequiredArgsConstructor
public class AttendanceDetailController {
    private final IAttendanceDetailService attendanceDetailService;
    @PostMapping("/update")
    public ResponseEntity<?> update (@RequestBody List<AttendanceDetailRequest> attendanceDetailRequests) throws CourseExistException {
        attendanceDetailService.updateAttendanceDetails(attendanceDetailRequests);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Điểm danh thành công"));
    }

//    @PostMapping("/update")
//    public ResponseEntity<?> update (@RequestBody AttendanceDetailRequest attendanceDetailRequests) throws CourseExistException {
//        attendanceDetailService.updateAttendanceDetails(attendanceDetailRequests);
//        return ResponseEntity.status(201).body(new ResponseAPI(true, "Điểm danh thành công"));
//    }
}
