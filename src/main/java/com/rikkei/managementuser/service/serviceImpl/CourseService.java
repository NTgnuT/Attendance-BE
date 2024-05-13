package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.CourseExistException;
import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.model.dto.request.CourseEditRequest;
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
                .title(c.getTitle())
                .courseTime(c.getCourseTime())
//                .startDate(c.getStartDate())
//                .endDate(c.getEndDate())
//                .status(CourseStatus.OPEN_FOR_REGISTRATION)
                .build();
        courseRepository.save(courses);
        return CourseResponse.builder()
                .id(courses.getId())
                .description(courses.getDescription())
                .title(courses.getTitle())
                .courseTime(courses.getCourseTime())
//                .startDate(courses.getStartDate())
//                .endDate(courses.getEndDate())
                .build();
    }

    //chỉnh sửa khóa học
    @Override
    public void edit(CourseEditRequest CR, Long id) throws CourseExistException {
        Courses c = courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm đươc khóa học với id cung cấp"));
        boolean isExist = courseRepository.existsByTitle(CR.getTitle()) && !c.getTitle().equals(CR.getTitle());
        if(isExist){
            throw new CourseExistException("Tên khóa học đã tồn tại");
        }
        c.setDescription(CR.getDescription());
        c.setCourseTime(CR.getCourseTime());
        c.setTitle(CR.getTitle());
//        c.setEndDate(CR.getEndDate());
//        c.setStartDate(CR.getStartDate());
        courseRepository.save(c);

    }

    @Override
    public CourseResponse findById(Long id) {
        Courses c = courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm được khóa học với id cung cấp"));
        return CourseResponse.builder()
                .id(c.getId())
                .title(c.getTitle())
                .description(c.getDescription())
                .courseTime(c.getCourseTime())
//                .endDate(c.getEndDate())
//                .startDate(c.getStartDate())
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
                .courseTime(a.getCourseTime())
                .description(a.getDescription())
                .id(a.getId())
//              .endDate(a.getEndDate())
//              .startDate(a.getStartDate())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> searchCourse(String keyword) {
        return courseRepository.findAllByTitleContainingOrDescriptionContaining(keyword, keyword).stream().map(a -> CourseResponse.builder()
                .title(a.getTitle())
                .description(a.getDescription())
                .id(a.getId())
                .courseTime(a.getCourseTime())
//              .endDate(a.getEndDate())
//              .startDate(a.getStartDate())
                .build()).collect(Collectors.toList());
    }


}
