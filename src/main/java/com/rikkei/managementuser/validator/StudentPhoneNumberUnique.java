package com.rikkei.managementuser.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StudentPhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StudentPhoneNumberUnique {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
