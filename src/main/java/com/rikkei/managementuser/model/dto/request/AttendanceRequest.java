package com.rikkei.managementuser.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceRequest {
//    @NonNull
//    @Min(1)
//    private Long teacherId;

    @NonNull
    @Min(1)
    private Long classId;

    @NonNull
    @Min(1)
    private Long moduleId;

}
