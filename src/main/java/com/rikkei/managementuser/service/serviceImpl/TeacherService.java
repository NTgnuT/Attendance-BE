package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.model.dto.request.TeacherRequest;
import com.rikkei.managementuser.model.dto.response.TeacherResponse;
import com.rikkei.managementuser.model.entity.Teacher;
import com.rikkei.managementuser.repository.ITeacherRepository;
import com.rikkei.managementuser.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TeacherService implements ITeacherService {
    private final ITeacherRepository instructorRepository;

    @Override
    public void save(TeacherRequest i) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(i.getDob());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        instructorRepository.save(Teacher.builder()
                .address(i.getAddress())
                .dob(date)
                .email(i.getEmail())
                .name(i.getName())
                .phoneNumber(i.getPhoneNumber())
                .build());
    }

    @Override
    public void edit(TeacherRequest i, Long id) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(i.getDob());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        findById(id);
        Teacher instructor = Teacher.builder()
                .phoneNumber(i.getPhoneNumber())
                .name(i.getName())
                .address(i.getAddress())
                .dob(date)
                .email(i.getEmail())
                .build();
        instructorRepository.save(instructor);

    }

    @Override
    public Teacher findById(Long id) {
        return instructorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy thông tin giáo viên với ID cung cấp!"));
    }

    @Override
    public void delete(Long id) {
        instructorRepository.delete(findById(id));
    }

    @Override
    public TeacherResponse instructorDetail(Long id) {
        Teacher i = findById(id);
        return TeacherResponse.builder()
                .instructorId(i.getId())
                .address(i.getAddress())
                .phoneNumber(i.getPhoneNumber())
                .email(i.getEmail())
                .dob(i.getDob())
                .build();
    }

    public Page<TeacherResponse> findAll(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 1, pageable.getSort());
        Page<Teacher> instructors = instructorRepository.findAll(pageable);

        return instructors.map(a -> TeacherResponse.builder()
                .instructorId(a.getId())
                .name(a.getName())
                .email(a.getEmail())
                .address(a.getAddress())
                .phoneNumber(a.getPhoneNumber())
                .dob(a.getDob())
                .build());

    }


}
