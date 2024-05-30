package com.rikkei.managementuser.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostRequest {
    private Long id;
    @NotBlank(message = "Tên bài viết không được để trống")
    private String name;
    private MultipartFile content;
}
