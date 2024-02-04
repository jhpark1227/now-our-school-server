package com.example.school.validation.validator;

import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.Facility;
import com.example.school.facility.service.FacilityQueryService;
import com.example.school.validation.annotation.ExistFacility;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FacilityExistValidator implements ConstraintValidator<ExistFacility, Long> {

    private final FacilityQueryService facilityQueryService;

    @Override
    public void initialize(ExistFacility constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<Facility> target = facilityQueryService.findFacility(value);

        if (target.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.FACILITY_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}