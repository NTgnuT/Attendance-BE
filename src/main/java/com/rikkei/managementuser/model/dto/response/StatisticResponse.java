package com.rikkei.managementuser.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticResponse {
    private Long studentId;
    private String studentName;
    private int present;
    private int absenceWithOutPermission;
    private int absenceWithPermission;
    private int percentAbsent;
}

