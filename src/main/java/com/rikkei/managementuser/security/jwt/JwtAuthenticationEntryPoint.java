package com.rikkei.managementuser.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

//        // Ghi log lỗi (Tùy chọn)
//        String message = "Responding with unauthorized error. Message - " + authException.getMessage();
//        log.error("Error->>> Authentication : ", authException.getMessage());
//
//        // Xây dựng một đối tượng JSON để gửi trong phản hồi
//        String jsonPayload = "{\"message\" : \"%s\", \"error\" : \"true\"}";
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getOutputStream().println(String.format(jsonPayload, authException.getMessage()));

        log.error("Error->>> Authentication : ", authException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Chưa được xác thực !");
        response.setStatus(403);
        new ObjectMapper().writeValue(response.getOutputStream(), map);
    }
}
