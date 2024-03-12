package com.rikkei.managementuser.validator;

import com.rikkei.managementuser.repository.IStudentRepository;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentPhoneNumberValidator implements ConstraintValidator<StudentPhoneNumberUnique,String> {
    private final IStudentRepository studentRepository ;
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !studentRepository.existsByPhoneNumber(string);
    }
}
