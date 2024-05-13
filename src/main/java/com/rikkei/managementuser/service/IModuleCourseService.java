package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.ModuleCourseNameException;
import com.rikkei.managementuser.model.dto.request.ModuleCourseEditRequest;
import com.rikkei.managementuser.model.dto.request.ModuleCourseRequest;
import com.rikkei.managementuser.model.dto.response.ModuleCourseResponse;

import java.util.List;

public interface IModuleCourseService {
    void save(ModuleCourseRequest moduleCourseRequest);

    void edit(ModuleCourseEditRequest moduleCourseEditRequest, Long id) throws ModuleCourseNameException;

    void delete(Long id);
    List<ModuleCourseResponse> findAllByCourseId(Long id);

    List<ModuleCourseResponse> findAll ();

}
