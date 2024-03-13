package com.rikkei.managementuser.validator;

import com.rikkei.managementuser.repository.ICourseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseUniqueValidator implements ConstraintValidator<CourseUnique,String> {
    private final ICourseRepository courseRepository;

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !courseRepository.existsByTitle(string);
    }
}
