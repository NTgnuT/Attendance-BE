package com.rikkei.managementuser.model.dto.request;

import com.rikkei.managementuser.validator.ClassUnique;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassCreateDTO {

    @NotBlank(message = "Không được bỏ trống")
    @ClassUnique(message = "Lớp học này đã tồn tại")
    private String name;

    @Min(value = 0, message = "số học viên không được âm ")
    private int maxStudent;

    @NotBlank(message = "Không được bỏ trống")
    private String instructor;

    private Long coursesId;

    private int status;

    private Date startTime;

}
