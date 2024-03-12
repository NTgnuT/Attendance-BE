package com.rikkei.managementuser.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rikkei.managementuser.validator.EmailUnique;
import com.rikkei.managementuser.validator.PhoneNumberUnique;
import com.rikkei.managementuser.validator.StudentEmailUnique;
import com.rikkei.managementuser.validator.StudentPhoneNumberUnique;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StudentRequest {
    @NotBlank(message = "Không được bỏ trống")
    private String name;

    @NotBlank(message = "Không được bỏ trống")
    private String address;

    @NotBlank(message = "Không được bỏ trống")
    @StudentPhoneNumberUnique(message = "SỐ điện thoại này đã có học sinh khác sử dụng")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không đúng định dạng")
    private String phoneNumber;

    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email không đúng định dạng")
    @StudentEmailUnique(message = "Email này đã có học sinh khác sử dụng")
    private String email;

    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date dob;
}
