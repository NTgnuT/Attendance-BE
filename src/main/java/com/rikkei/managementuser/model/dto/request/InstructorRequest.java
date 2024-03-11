package com.rikkei.managementuser.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rikkei.managementuser.validator.EmailUnique;
import com.rikkei.managementuser.validator.PhoneNumberUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorRequest {
    @NotBlank
    private String name;

    @NotBlank
    @EmailUnique
    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email không đúng định dạng")
    private String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không đúng định dạng")
    @NotBlank
    @PhoneNumberUnique
    private String phoneNumber;

    @NotBlank
    private String address;

    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date dob;
}
