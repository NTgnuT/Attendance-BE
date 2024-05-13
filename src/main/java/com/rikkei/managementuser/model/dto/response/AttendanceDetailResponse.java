package com.rikkei.managementuser.model.dto.response;

import com.rikkei.managementuser.model.entity.Attendance;
import com.rikkei.managementuser.model.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDetailResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private AttendanceStatus status;
}
