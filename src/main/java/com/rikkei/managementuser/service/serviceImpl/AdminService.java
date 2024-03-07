package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.repository.IAdminRepository;
import com.rikkei.managementuser.repository.ICourseRepository;
import com.rikkei.managementuser.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {
    private final ICourseRepository courseRepository;



}
