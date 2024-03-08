package com.rikkei.managementuser.validator;

import com.rikkei.managementuser.repository.IClassRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;

@Component
@RequiredArgsConstructor
public class ClassValidator implements ConstraintValidator<ClassUnique,String> {
    private final IClassRepository classRepository;

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {

       return !classRepository.existsByName(string);
    }
}
