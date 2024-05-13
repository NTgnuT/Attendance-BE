package com.rikkei.managementuser.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceDetailRequest {
    private Long id;
//    private List<String> status;
    private String status;
    private Long studentId;
    private Long scheduleId;
}
