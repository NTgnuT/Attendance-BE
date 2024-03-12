package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.request.StudentEditRequest;
import com.rikkei.managementuser.model.dto.request.StudentRequest;
import com.rikkei.managementuser.model.entity.Student;
import com.rikkei.managementuser.repository.IStudentRepository;
import com.rikkei.managementuser.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final IStudentRepository studentRepository;

    @Override
    public void save(StudentRequest s) {
        studentRepository.save(Student.builder()
                .address(s.getAddress())
                .dob(s.getDob())
                .email(s.getEmail())
                .phoneNumber(s.getPhoneNumber())
                .name(s.getName())
                .build());
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(findById(id));
    }

    @Override
    public void edit(Long id, StudentEditRequest s) throws EmailAndPhoneException, PhoneUniqueException, EmailUniqueException {
        Student student = findById(id);
        //nếu người dùng thay đồi tài khoản
        boolean isPhoneExist = studentRepository.existsByPhoneNumber(s.getPhoneNumber())&&!(findById(id).getPhoneNumber().equals(s.getPhoneNumber()));
        boolean test = studentRepository.existsByEmail(s.getEmail());
        boolean test2 =! findById(id).getEmail().equals(s.getEmail());
        boolean isEmailExist = studentRepository.existsByEmail(s.getEmail())&&!(findById(id).getEmail().equals(s.getEmail()));

        if(isPhoneExist && isEmailExist ){
            throw new EmailAndPhoneException("Số điện thoại va email đã tồn tại");
        }else if(isPhoneExist){
            throw  new PhoneUniqueException("Số điện thoại đã tồn tại ");
        }else if (isEmailExist){
            throw new EmailUniqueException("Email đã tồn tại");
        }
//        if(studentRepository.existsByPhoneNumber(student.getPhoneNumber())&&!(student.getPhoneNumber().equals(s.getPhoneNumber()))
//        || studentRepository.existsByEmail(student.getEmail())&&!()){
//
//        }else if(){
//            throw
//        }
        student.setName(s.getName());
        student.setDob(s.getDob());
        student.setAddress(s.getAddress());
        student.setPhoneNumber(s.getPhoneNumber());
        student.setEmail(s.getEmail());
        studentRepository.save(student);
    }

    Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại học sinh với ID này"));
    }

}
