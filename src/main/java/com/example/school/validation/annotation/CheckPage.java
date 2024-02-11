package com.example.school.validation.annotation;

import com.example.school.validation.validator.PageCheckValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PageCheckValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPage {
    String message() default "잘못된 페이지입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}