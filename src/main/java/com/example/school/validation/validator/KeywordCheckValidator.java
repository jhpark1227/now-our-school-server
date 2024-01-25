package com.example.school.validation.validator;


import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.validation.annotation.CheckKeyword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class KeywordCheckValidator implements ConstraintValidator<CheckKeyword,String> {

    @Override
    public void initialize(CheckKeyword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null||value.trim().equals("")){
            context.disableDefaultConstraintViolation();
            throw new GeneralException(ErrorStatus.SEARCH_CONDITION_ERROR);
        }
        return true;
    }
}
