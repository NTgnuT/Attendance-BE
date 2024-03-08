package com.rikkei.managementuser.model.dto.response;

import com.rikkei.managementuser.model.entity.Courses;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassResponse {
    private Long id;

    private String name;

    private int maxStudent;

    private String instructorName;

    private Long courses;

}
