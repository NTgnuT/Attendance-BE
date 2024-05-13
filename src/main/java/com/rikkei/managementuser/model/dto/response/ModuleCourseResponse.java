package com.rikkei.managementuser.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleCourseResponse {
    private Long id;
    private String moduleName;
    private int time;
    private Long courseId;
}
