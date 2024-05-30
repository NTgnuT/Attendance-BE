package com.rikkei.managementuser.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    String uploadContent (MultipartFile file);
}
