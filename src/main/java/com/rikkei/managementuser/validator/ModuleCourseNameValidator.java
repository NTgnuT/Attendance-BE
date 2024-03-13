package com.rikkei.managementuser.validator;

import com.rikkei.managementuser.repository.IModuleCourseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModuleCourseNameValidator implements ConstraintValidator<ModuleCourseNameUnique,String> {
    private final IModuleCourseRepository moduleCourseRepository;
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !moduleCourseRepository.existsByModuleName(string);
    }
}
