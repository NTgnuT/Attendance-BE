package com.rikkei.managementuser.validator;

import com.rikkei.managementuser.repository.ITeacherRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberUnique, String> {
    private final ITeacherRepository instructorRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !instructorRepository.existsByPhoneNumber(value);

    }
}
