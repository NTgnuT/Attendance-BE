package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.model.dto.request.InstructorRequest;
import com.rikkei.managementuser.model.dto.response.InstructorResponse;
import com.rikkei.managementuser.model.entity.Instructor;
import com.rikkei.managementuser.repository.IInstructorRepository;
import com.rikkei.managementuser.service.IInstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InstructorService implements IInstructorService {
    private final IInstructorRepository instructorRepository;

    @Override
    public void save(InstructorRequest i) {
        instructorRepository.save(Instructor.builder()
                .address(i.getAddress())
                .dob(i.getDob())
                .email(i.getEmail())
                .name(i.getName())
                .phoneNumber(i.getPhoneNumber())
                .build());
    }

    @Override
    public void edit(InstructorRequest i, Long id) {
        findById(id);
        Instructor instructor = Instructor.builder()
                .phoneNumber(i.getPhoneNumber())
                .name(i.getName())
                .address(i.getAddress())
                .dob(i.getDob())
                .email(i.getEmail())
                .build();
        instructorRepository.save(instructor);

    }

    @Override
    public Instructor findById(Long id) {
        return instructorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy thông tin giáo viên với ID cung cấp!"));
    }

    @Override
    public void delete(Long id) {
        instructorRepository.delete(findById(id));
    }

    @Override
    public InstructorResponse instructorDetail(Long id) {
        Instructor i = findById(id);
        return InstructorResponse.builder()
                .instructorId(i.getInstructorId())
                .address(i.getAddress())
                .phoneNumber(i.getPhoneNumber())
                .email(i.getEmail())
                .dob(i.getDob())
                .build();
    }

    public Page<InstructorResponse> findAll(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 1, pageable.getSort());
        Page<Instructor> instructors = instructorRepository.findAll(pageable);

        return instructors.map(a -> InstructorResponse.builder()
                .instructorId(a.getInstructorId())
                .name(a.getName())
                .email(a.getEmail())
                .address(a.getAddress())
                .phoneNumber(a.getPhoneNumber())
                .dob(a.getDob())
                .build());

    }


}
