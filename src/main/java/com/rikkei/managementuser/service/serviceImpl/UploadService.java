package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.service.IUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService implements IUploadService {
    @Value("${path-upload}")
    private String pathUpload;
    @Value("${server.port}")
    private String port;
    @Override
    public String uploadContent(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(pathUpload + fileName));
            return "http://localhost:" + port + "/img/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
