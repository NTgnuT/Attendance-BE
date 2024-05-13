package com.rikkei.managementuser.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassResponse {
    private Long id;

    private String name;

    private int maxStudent;

//    private String instructorName;

    private Long courses;

    private Date startTime;

}
