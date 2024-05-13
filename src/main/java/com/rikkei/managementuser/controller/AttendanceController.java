package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.model.entity.Student;
import com.rikkei.managementuser.service.IAttendanceDetailService;
import com.rikkei.managementuser.service.IAttendanceService;
import com.rikkei.managementuser.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user-management/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final IAttendanceService attendanceService;
    @PostMapping("")
    public ResponseEntity<?> findAtt (@RequestBody AttendanceRequest a) {
        return ResponseEntity.status(201).body(attendanceService.findAttendance(a));
    }
//    @PostMapping("")
//    public ResponseEntity<?> create (@RequestBody AttendanceRequest a) {
//        return ResponseEntity.status(201).body(attendanceService.save(a));
//    }
}
