package com.rikkei.managementuser.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignIn {
//    @NotBlank(message = "Email không được bỏ trống")
//    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email không đúng định dạng(a@gmail.com)")
    private String email;
//    @NotBlank(message = "Mật khẩu không đươc để trống")
//    @Pattern(regexp = ".{6,}")
    private String password;
}
