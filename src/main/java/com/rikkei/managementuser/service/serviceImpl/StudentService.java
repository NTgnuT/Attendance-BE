package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.request.StudentEditRequest;
import com.rikkei.managementuser.model.dto.request.StudentRequest;
import com.rikkei.managementuser.model.entity.Student;
import com.rikkei.managementuser.repository.IClassRepository;
import com.rikkei.managementuser.repository.IStudentRepository;
import com.rikkei.managementuser.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final IStudentRepository studentRepository;
    private final IClassRepository classRepository;

    @Override
    public void save(StudentRequest s) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(s.getDob());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        studentRepository.save(Student.builder()
                .address(s.getAddress())
                .dob(date)
                .email(s.getEmail())
                .phoneNumber(s.getPhoneNumber())
                .name(s.getName())
                .status(1)
                .created_at(new Date())
                .updated_at(new Date())
                .aClass(classRepository.findById(s.getClassId()).orElseThrow(() -> new NoSuchElementException("Không tồn tại id lớp học này ")))
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
        boolean isPhoneExist = studentRepository.existsByPhoneNumber(s.getPhoneNumber()) && !(findById(id).getPhoneNumber().equals(s.getPhoneNumber()));
        boolean test = studentRepository.existsByEmail(s.getEmail());
        boolean test2 = !findById(id).getEmail().equals(s.getEmail());
        boolean isEmailExist = studentRepository.existsByEmail(s.getEmail()) && !(findById(id).getEmail().equals(s.getEmail()));

        if (isPhoneExist && isEmailExist) {
            throw new EmailAndPhoneException("Số điện thoại va email đã tồn tại");
        } else if (isPhoneExist) {
            throw new PhoneUniqueException("Số điện thoại đã tồn tại ");
        } else if (isEmailExist) {
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
        student.setUpdated_at(new Date());
        student.setAClass(classRepository.findById(s.getClassId()).orElseThrow(() -> new NoSuchElementException("Không tồn tại id lớp học này ")));
        student.setCreated_at(student.getCreated_at());
        studentRepository.save(student);
    }

    @Override
    public List<Student> findByClassId(Long id) {
        return studentRepository.findByAClass_Id(id);
    }

    Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại học sinh với ID này"));
    }
    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

}
