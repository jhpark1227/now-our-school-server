package com.example.school.validation.validator;


import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.entity.enums.FaqType;
import com.example.school.validation.annotation.CheckFaqType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

public class FaqTypeCheckValidator implements ConstraintValidator<CheckFaqType,String> {

    @Override
    public void initialize(CheckFaqType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null||value.trim().equals("")||!EnumUtils.isValidEnum(FaqType.class,value.toUpperCase())){
            context.disableDefaultConstraintViolation();
            throw new GeneralException(ErrorStatus.BAD_QUERY_STRING);
        }
        return true;
    }
}
