package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.ModuleCourseNameException;
import com.rikkei.managementuser.model.dto.request.ModuleCourseEditRequest;
import com.rikkei.managementuser.model.dto.request.ModuleCourseRequest;
import com.rikkei.managementuser.model.dto.response.CourseResponse;
import com.rikkei.managementuser.model.dto.response.ModuleCourseResponse;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.repository.IModuleCourseRepository;
import com.rikkei.managementuser.service.IModuleCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-management/api/module-courses")
@RequiredArgsConstructor
public class ModuleCourseController {
    private final IModuleCourseService moduleCourseService;

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody ModuleCourseRequest moduleCourseRequest) {
        moduleCourseService.save(moduleCourseRequest);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Đã tạo Module Course thành công !"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody ModuleCourseEditRequest moduleCourseEditRequest) throws ModuleCourseNameException {
        moduleCourseService.edit(moduleCourseEditRequest, id);
        return ResponseEntity.status(200).body(new ResponseAPI(true, "Đã thay đổi thah2 công "));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        moduleCourseService.delete(id);
        return ResponseEntity.status(200).body(new ResponseAPI(true, "Đã xóa thành công "));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findAllByCourseId(@PathVariable Long id) {
        List<ModuleCourseResponse> mdr = moduleCourseService.findAllByCourseId(id);
        return ResponseEntity.status(200).body(mdr);
    }

    @GetMapping("")
    public ResponseEntity<?> findAllModuleCourse() {
        return ResponseEntity.status(200).body(moduleCourseService.findAll());
    }
}
