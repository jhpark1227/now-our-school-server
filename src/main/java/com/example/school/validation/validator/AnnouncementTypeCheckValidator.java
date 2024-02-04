package com.example.school.validation.validator;


import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.enums.AnnouncementType;
import com.example.school.validation.annotation.CheckAnnouncementType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

public class AnnouncementTypeCheckValidator implements ConstraintValidator<CheckAnnouncementType,String> {

    @Override
    public void initialize(CheckAnnouncementType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value!=null){
            if(value.trim().equals("")||!EnumUtils.isValidEnum(AnnouncementType.class,value)){
                context.disableDefaultConstraintViolation();
                throw new GeneralException(ErrorStatus.BAD_QUERY_STRING);
            }
        }
        return true;
    }
}
