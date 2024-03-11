package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.model.dto.request.InstructorRequest;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.service.IInstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user-management/api/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final IInstructorService instructorService;

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody InstructorRequest instructorRequest) {
        instructorService.save(instructorRequest);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Giáo viên đã được tạo thành công"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody InstructorRequest instructorRequest) {
        instructorService.edit(instructorRequest, id);
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
