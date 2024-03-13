package com.rikkei.managementuser.model.dto.response;

import com.rikkei.managementuser.model.entity.CourseStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    private int courseTime;

//    @Temporal(TemporalType.DATE)
//    private Date startDate;
//
//    @Temporal(TemporalType.DATE)
//    private Date endDate;


}
