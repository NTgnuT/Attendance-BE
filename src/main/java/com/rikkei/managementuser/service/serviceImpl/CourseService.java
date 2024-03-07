package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.model.dto.request.CourseRequest;
import com.rikkei.managementuser.model.dto.response.CourseResponse;
import com.rikkei.managementuser.model.entity.CourseStatus;
import com.rikkei.managementuser.model.entity.Courses;
import com.rikkei.managementuser.model.entity.Roles;
import com.rikkei.managementuser.repository.ICourseRepository;
import com.rikkei.managementuser.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    private final ICourseRepository courseRepository;

    @Override
    public CourseResponse save(CourseRequest c) {
        Courses courses = Courses.builder()
                .description(c.getDescription())
                .startDate(c.getStartDate())
                .endDate(c.getEndDate())
                .title(c.getTitle())
                .status(CourseStatus.OPEN_FOR_REGISTRATION)
                .build();
        courseRepository.save(courses);
        return CourseResponse.builder()
                .id(courses.getCourseId())
                .description(courses.getDescription())
                .startDate(courses.getStartDate())
                .endDate(courses.getEndDate())
                .title(courses.getTitle())
                .build();
    }

    //chỉnh sửa khóa học
    @Override
    public void edit(CourseRequest CR, Long id) {
        Courses c = courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm đươc khóa học với id cung cấp"));
        c.setDescription(CR.getDescription());
        c.setEndDate(CR.getEndDate());
        c.setStartDate(CR.getStartDate());
        c.setTitle(CR.getTitle());
        courseRepository.save(c);

    }

    @Override
    public CourseResponse findById(Long id) {
        Courses c = courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm được khóa học với id cung cấp"));
        return CourseResponse.builder()
                .id(c.getCourseId())
                .title(c.getTitle())
                .endDate(c.getEndDate())
                .description(c.getDescription())
                .startDate(c.getStartDate())
                .build();

    }

    @Override
    public void deleteCourse(Long id) throws NoPermissionToDelete {
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm đươc khóa học với id cung cấp")));
        } else {
            throw new NoPermissionToDelete("Bạn không có quyền xóa khóa học này");
        }
    }

    @Override
    public List<CourseResponse> findAll() {
        return courseRepository.findAll().stream().map(a -> CourseResponse.builder()
                .title(a.getTitle())
                .endDate(a.getEndDate())
                .startDate(a.getStartDate())
                .description(a.getDescription())
                .id(a.getCourseId())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> searchCourse(String keyword){
     return courseRepository.findAllByTitleContainingOrDescriptionContaining(keyword, keyword).stream().map(a -> CourseResponse.builder()
                .title(a.getTitle())
                .endDate(a.getEndDate())
                .startDate(a.getStartDate())
                .description(a.getDescription())
                .id(a.getCourseId())
                .build()).collect(Collectors.toList());
    }


}
