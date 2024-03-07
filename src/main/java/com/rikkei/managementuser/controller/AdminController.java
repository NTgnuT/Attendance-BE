package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.model.dto.request.CourseRequest;
import com.rikkei.managementuser.model.dto.response.CourseResponse;
import com.rikkei.managementuser.model.dto.response.CreateCourseDTO;
import com.rikkei.managementuser.service.ICourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.List;

@RestController
@RequestMapping("/user-management/api")
@RequiredArgsConstructor
public class AdminController {
    private final ICourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<?> addCourse(@Valid @RequestBody CourseRequest courseRequest) {
        courseService.save(courseRequest);
        return ResponseEntity.status(201).body(new CreateCourseDTO(true, "Khóa học đã được tạo thành công"));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> editCourse(@Valid @RequestBody CourseRequest courseRequest, @PathVariable Long id) {
        courseService.edit(courseRequest, id);
        return ResponseEntity.status(200).body(new CreateCourseDTO(true, "Thông tin khóa học đã được cập nhật thành công"));
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) throws NoPermissionToDelete {
        courseService.deleteCourse(id);
        return ResponseEntity.status(204).body(null);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> findCourse(@PathVariable Long id) {
        return ResponseEntity.status(200).body(courseService.findById(id));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> findAllCourse() {
        return ResponseEntity.status(200).body(courseService.findAll());
    }
    @GetMapping("/courses/search")
    public ResponseEntity<?> searchCourse(@RequestParam String keyword) {
        try{
            return ResponseEntity.status(200).body(courseService.searchCourse(keyword));
        }catch (Exception ex){
            return ResponseEntity.status(500).body(null);
        }
    }

}
