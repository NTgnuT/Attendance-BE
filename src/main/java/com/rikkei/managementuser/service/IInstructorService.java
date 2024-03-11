package com.rikkei.managementuser.service;

import com.rikkei.managementuser.model.dto.request.InstructorRequest;
import com.rikkei.managementuser.model.dto.response.InstructorResponse;
import com.rikkei.managementuser.model.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IInstructorService {
    void save(InstructorRequest instructorRequest);
    void edit(InstructorRequest instructorRequest, Long id);
    Instructor findById(Long id);
    void delete(Long id);
    InstructorResponse instructorDetail(Long id);
    Page<InstructorResponse> findAll(Pageable pageable);

}
