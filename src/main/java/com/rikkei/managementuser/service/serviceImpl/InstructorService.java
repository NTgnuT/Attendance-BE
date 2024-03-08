package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.repository.IInstructorRepository;
import com.rikkei.managementuser.service.IInstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService implements IInstructorService {
    private final IInstructorRepository instructor;

}
