package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.model.dto.request.ClassCreateDTO;
import com.rikkei.managementuser.model.dto.request.ClassEditDTO;
import com.rikkei.managementuser.model.dto.response.ClassResponse;
import com.rikkei.managementuser.model.entity.Class;
import com.rikkei.managementuser.repository.IClassRepository;
import com.rikkei.managementuser.repository.ICourseRepository;
import com.rikkei.managementuser.repository.ITeacherRepository;
import com.rikkei.managementuser.service.IClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService implements IClassService {
    private final IClassRepository classRepository;
    private final ITeacherRepository instructorRepository;
    private final ICourseRepository courseRepository;


    @Override
    public void save(ClassCreateDTO c) {
        Class aClass = Class.builder()
                .courses(courseRepository.findById(c.getCoursesId()).orElseThrow(() -> new NoSuchElementException("Không tồn tại khóa học này ")))
                .maxStudent(c.getMaxStudent())
                .name(c.getName())
                .startTime(new Date())
                .status(1)
                .build();
        classRepository.save(aClass);
    }

    @Override
    public ClassResponse findById(Long classId) throws NoSuchElementException {
        Class aClass = classRepository.findById(classId).orElseThrow(() -> new NoSuchElementException("Không tồn tại thông tin lớp học với ID cung cấp"));
        return ClassResponse.builder()
                .id(aClass.getId())
                .maxStudent(aClass.getMaxStudent())
                .courses(aClass.getCourses().getId())
                .startTime(aClass.getStartTime())
                .name(aClass.getName())
                .build();
    }

    @Override
    public List<ClassResponse> findAllClass() {
        return classRepository.findAll().stream().map(a -> ClassResponse.builder()
                .id(a.getId())
                .name(a.getName())
                .courses(a.getCourses().getId())
                .startTime(a.getStartTime())
                .maxStudent(a.getMaxStudent())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void editClass(ClassEditDTO a, Long id) {
        Class aClass = classRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại lớp học này "));
        aClass.setName(a.getName());
        aClass.setCourses(courseRepository.findById(a.getCoursesId()).orElseThrow(() -> new NoSuchElementException("Không tồn tại khóa học này ")));
        aClass.setStartTime(a.getStartTime());
        aClass.setStatus(a.getStatus());
        aClass.setMaxStudent(a.getMaxStudent());
        classRepository.save(aClass);
    }

    @Override
    public void deleteClass(Long id) throws NoPermissionToDelete {
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            classRepository.delete(classRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tòn tại lớp học này!")));
        } else {
            throw new NoPermissionToDelete("Bạn không có quyền xóa lớp học này!");
        }
    }

    @Override
    public List<ClassResponse> search(String keyword) {
        return classRepository.findAllByNameContaining(keyword)
                .stream().map(a -> ClassResponse.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .courses(a.getCourses().getId())
                        .maxStudent(a.getMaxStudent())
                        .startTime(a.getStartTime())
                        .build()).collect(Collectors.toList());
    }


}
