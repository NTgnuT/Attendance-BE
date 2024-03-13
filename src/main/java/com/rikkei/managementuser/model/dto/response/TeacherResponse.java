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
public class TeacherResponse {

    private Long instructorId;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private Date dob;
}
