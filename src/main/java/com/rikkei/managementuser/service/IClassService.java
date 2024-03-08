package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.model.dto.request.ClassCreateDTO;
import com.rikkei.managementuser.model.dto.response.ClassResponse;
import com.rikkei.managementuser.model.entity.Class;

import java.util.List;
import java.util.Optional;

public interface IClassService {
    void save(ClassCreateDTO classCreateDTO);
    ClassResponse findById(Long classId);

    List<ClassResponse> findAllClass();
    void editClass(ClassCreateDTO classCreateDTO,Long id);
    void deleteClass(Long id) throws NoPermissionToDelete;
    List<ClassResponse> search(String keyword);


}
