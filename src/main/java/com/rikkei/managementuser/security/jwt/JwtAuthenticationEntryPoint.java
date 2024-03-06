package com.rikkei.managementuser.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Ghi log lỗi (Tùy chọn)
        String message = "Responding with unauthorized error. Message - " + authException.getMessage();
        log.error("Error->>> Authentication : ", authException.getMessage());

        // Xây dựng một đối tượng JSON để gửi trong phản hồi
        String jsonPayload = "{\"message\" : \"%s\", \"error\" : \"true\"}";
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(String.format(jsonPayload, authException.getMessage()));
    }
}
