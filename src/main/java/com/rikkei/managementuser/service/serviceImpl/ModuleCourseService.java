package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.ModuleCourseNameException;
import com.rikkei.managementuser.model.dto.request.ModuleCourseEditRequest;
import com.rikkei.managementuser.model.dto.request.ModuleCourseRequest;
import com.rikkei.managementuser.model.entity.ModuleCourse;
import com.rikkei.managementuser.repository.ICourseRepository;
import com.rikkei.managementuser.repository.IModuleCourseRepository;
import com.rikkei.managementuser.service.IModuleCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ModuleCourseService implements IModuleCourseService {
    private final IModuleCourseRepository moduleCourseRepository;
    private final ICourseRepository courseRepository;

    @Override
    public void save(ModuleCourseRequest m) {
        ModuleCourse moduleCourse = ModuleCourse.builder()
                .moduleName(m.getModuleName())
                .time(m.getTime())
                .course(courseRepository.findById(m.getCourseId()).orElseThrow(() -> new NoSuchElementException("Không tồn tại khóa học với ID này!")))
                .build();
        moduleCourseRepository.save(moduleCourse);
    }

    @Override
    public void edit(ModuleCourseEditRequest m, Long id) throws ModuleCourseNameException {
        ModuleCourse moduleCourse = moduleCourseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại Module với ID này!"));
        boolean moduleNameExist = !m.getModuleName().equals(moduleCourse.getModuleName()) && moduleCourseRepository.existsByModuleName(m.getModuleName());
        if (moduleNameExist) {
            throw new ModuleCourseNameException("Tên khóa học đã tồn tại");
        }
        moduleCourse.setModuleName(m.getModuleName());
        moduleCourse.setTime(m.getTime());
        moduleCourse.setCourse(courseRepository.findById(m.getCourseId()).orElseThrow(() -> new NoSuchElementException("Không tồn tại khóa học với ID này!")));
        moduleCourseRepository.save(moduleCourse);
    }

    @Override
    public void delete(Long id) {
        ModuleCourse moduleCourse = moduleCourseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại Module với ID đã cung cấp!"));
        moduleCourseRepository.delete(moduleCourse);
    }

}
