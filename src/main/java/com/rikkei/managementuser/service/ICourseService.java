package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.CourseExistException;
import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.model.dto.request.CourseEditRequest;
import com.rikkei.managementuser.model.dto.request.CourseRequest;
import com.rikkei.managementuser.model.dto.response.CourseResponse;
import com.rikkei.managementuser.model.entity.Courses;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    CourseResponse save(CourseRequest courseRequest);

    void edit(CourseEditRequest courseRequest, Long id) throws CourseExistException;

    CourseResponse findById(Long id);

    void deleteCourse(Long id) throws NoPermissionToDelete;

    List<CourseResponse> findAll();

    List<CourseResponse> searchCourse(String keyword);
}
