package com.rikkei.managementuser.validator;

import com.rikkei.managementuser.repository.ICourseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseExistValidator implements ConstraintValidator<CourseExist, Long> {
    private final ICourseRepository courseRepository;

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return courseRepository.existsById(aLong);
    }
}
