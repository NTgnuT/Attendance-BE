package com.rikkei.managementuser.model.dto.request;

import com.rikkei.managementuser.validator.CourseUnique;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CourseEditRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;

    private int courseTime;
}
