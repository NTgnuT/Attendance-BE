package com.rikkei.managementuser.service;

import com.rikkei.managementuser.model.dto.request.TeacherRequest;
import com.rikkei.managementuser.model.dto.response.TeacherResponse;
import com.rikkei.managementuser.model.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITeacherService {
    void save(TeacherRequest teacherRequest);
    void edit(TeacherRequest teacherRequest, Long id);
    Teacher findById(Long id);
    void delete(Long id);
    TeacherResponse instructorDetail(Long id);
    Page<TeacherResponse> findAll(Pageable pageable);

}
