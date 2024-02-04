package com.example.school.validation.validator;

import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.enums.FacilityKeyword;
import com.example.school.validation.annotation.ExistKeyword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class KeywordExistValidator implements ConstraintValidator<ExistKeyword, String> {

    @Override
    public void initialize(ExistKeyword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (!EnumUtils.isValidEnum(FacilityKeyword.class,value.toUpperCase())){
            context.disableDefaultConstraintViolation();
            throw new GeneralException(ErrorStatus.BAD_REQUEST);
        }
        return true;
    }
}