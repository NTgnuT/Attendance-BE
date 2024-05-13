package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.request.StudentEditRequest;
import com.rikkei.managementuser.model.dto.request.StudentRequest;
import com.rikkei.managementuser.model.entity.Student;

import java.util.List;

public interface IStudentService {

    void save(StudentRequest studentRequest);
    void delete(Long id);
    void edit(Long id, StudentEditRequest studentRequest) throws PhoneUniqueException, EmailUniqueException, EmailAndPhoneException, PhoneEditUniqueException, EmailEditUniqueException;
    List<Student> findByClassId (Long id);
    List<Student> findAll();
}

