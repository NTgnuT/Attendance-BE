package com.rikkei.managementuser.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticRequest {
    private Long classId;
    private Long moduleId;
}
