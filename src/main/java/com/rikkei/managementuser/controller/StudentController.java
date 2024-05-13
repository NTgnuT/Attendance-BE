package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.request.StudentEditRequest;
import com.rikkei.managementuser.model.dto.request.StudentRequest;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.repository.IStudentRepository;
import com.rikkei.managementuser.service.ExcelService;
import com.rikkei.managementuser.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user-management/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final IStudentService studentService;
    private final ExcelService excelService;

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody StudentRequest studentRequest){
        studentService.save(studentRequest);
        return ResponseEntity.status(201).body(new ResponseAPI(true,"Thêm mới thành công !"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        studentService.delete(id);
        return ResponseEntity.status(200).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit( @PathVariable Long id,@Valid @RequestBody StudentEditRequest studentRequest) throws EmailUniqueException, PhoneUniqueException, PhoneEditUniqueException, EmailAndPhoneException, EmailEditUniqueException {
        studentService.edit(id, studentRequest);
        return ResponseEntity.status(200).body(new ResponseAPI(true,"Thay đổi thông tin thành công"));
    }

    @PostMapping("/import-excel")
    public ResponseEntity<?> importExcelFile(@RequestParam("file") MultipartFile file){
        try{
            excelService.importToDatabase(file);
            return ResponseEntity.status(200).body("Đã thêm thành công từ file ");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Lỗi xãy ra trong quá trình !");
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getAllStudent(){
        return ResponseEntity.status(200).body(studentService.findAll());
    }
}
