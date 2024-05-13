package com.rikkei.managementuser.model.dto.response;

import com.rikkei.managementuser.model.entity.AttendanceDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceResponse {
    private Long id;
    private Long scheduleId;
    private Date timeAttendance;
    private List<AttendanceDetailResponse> attendanceDetailResponses;
}
