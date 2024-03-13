package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.model.dto.request.TeacherRequest;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.service.ITeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-management/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final ITeacherService instructorService;

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody TeacherRequest teacherRequest) {
        instructorService.save(teacherRequest);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Giáo viên đã được tạo thành công"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody TeacherRequest teacherRequest) {
        instructorService.edit(teacherRequest, id);
        return ResponseEntity.status(200).body(new ResponseAPI(true, "Thông tin giáo viên đã cập nhật thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        instructorService.delete(id);
        return ResponseEntity.status(204).body(null);
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(Pageable pageable){
        return ResponseEntity.status(200).body(instructorService.findAll(pageable));
    }

}
