package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.dto.response.AttendanceDetailResponse;
import com.rikkei.managementuser.model.dto.response.AttendanceResponse;
import com.rikkei.managementuser.model.entity.*;
import com.rikkei.managementuser.repository.*;
import com.rikkei.managementuser.service.IAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService implements IAttendanceService {
    private final IAttendanceRepository attendanceRepository;
    private final IScheduleRepository scheduleRepository;
    private final IAttendanceDetailRepository attendanceDetailRepository;

    @Override
    public AttendanceResponse findById(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow(() -> new NoSuchElementException("Không tồn tại buổi điểm danh với ID cung cấp"));
        return AttendanceResponse.builder()
                .id(attendance.getId())
                .scheduleId(attendance.getSchedule().getId())
                .timeAttendance(attendance.getTimeAttendance())
                .build();
    }

    @Override
    public List<AttendanceResponse> findAllAttendance() {
        return attendanceRepository.findAll().stream().map(att -> AttendanceResponse.builder()
                .id(att.getId())
                .scheduleId(att.getSchedule().getId())
                .timeAttendance(att.getTimeAttendance())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> findAttendance(AttendanceRequest attRequest) {
        Schedule schedule = scheduleRepository.findScheduleByClassIdAndModuleId(attRequest.getClassId(), attRequest.getModuleId());
        if (schedule != null) {
            List<Attendance> attendances = attendanceRepository.findAllBySchedule_Id(schedule.getId());

            return attendances.stream().map(attResponse -> AttendanceResponse.builder()
                    .id(attResponse.getId())
                    .scheduleId(attResponse.getSchedule().getId())
                    .timeAttendance(attResponse.getTimeAttendance())
                    .attendanceDetailResponses(mapperAttendanceDetailToAttendanceDetailResponse(attendanceDetailRepository.findAttendanceDetailByAttendance_Id(attResponse.getId())))
                    .build()).collect(Collectors.toList());
        } else {
            throw new NoSuchElementException("Không tìm thấy lịch học này");
        }
    }

    public List<AttendanceDetailResponse> mapperAttendanceDetailToAttendanceDetailResponse(List<AttendanceDetail> attendanceDetails) {
        return attendanceDetails.stream().map(att -> AttendanceDetailResponse.builder()
                .id(att.getId())
                .studentId(att.getStudent().getId())
                .studentName(att.getStudent().getName())
                .status(att.getAttendanceStatus())
                .build()).collect(Collectors.toList());
    }
}
