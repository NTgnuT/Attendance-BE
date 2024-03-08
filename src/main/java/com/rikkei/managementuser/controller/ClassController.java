package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.model.dto.ErrorResponse;
import com.rikkei.managementuser.model.dto.request.ClassCreateDTO;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.service.IClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user-management/api/classes")
@RequiredArgsConstructor

public class ClassController {
    private final IClassService classService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> addClass(@Valid @RequestBody ClassCreateDTO classCreateDTO) {
        try {
            classService.save(classCreateDTO);
            return ResponseEntity.status(201).body(new ResponseAPI(true, "Lớp học đă đăng ký thàng công"));
        } catch (HttpServerErrorException.InternalServerError ex) {
            return ResponseEntity.status(500).body(new ResponseAPI(false, "lỗi sever"));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editClass(@Valid @RequestBody ClassCreateDTO classCreateDTO, @PathVariable Long id) {
        classService.editClass(classCreateDTO, id);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Lớp học đă thay đổi thàng công"));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) throws NoPermissionToDelete {
        classService.deleteClass(id);
        return ResponseEntity.status(204).body(null);
    }

    @GetMapping("")
    public ResponseEntity<?> findAllClass() {
        classService.findAllClass();
        return ResponseEntity.status(200).body(classService.findAllClass());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findClass(@PathVariable Long id) {
        return ResponseEntity.status(200).body(classService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchClass(@RequestParam String keyword) {
        return ResponseEntity.status(200).body(classService.search(keyword));
    }

}
