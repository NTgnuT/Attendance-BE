package com.rikkei.managementuser.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rikkei.managementuser.validator.StudentEmailUnique;
import com.rikkei.managementuser.validator.StudentPhoneNumberUnique;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEditRequest {
    @NotBlank(message = "Không được bỏ trống")
    private String name;

    @NotBlank(message = "Không được bỏ trống")
    private String address;

    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không đúng định dạng")
    private String phoneNumber;

    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email không đúng định dạng")
    private String email;

    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date dob;

    @Min(1)
    private Long classId;
}
