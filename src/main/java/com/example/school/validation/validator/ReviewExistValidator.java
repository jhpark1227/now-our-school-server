package com.example.school.validation.validator;

import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.Review;
import com.example.school.user.service.UserQueryService;
import com.example.school.validation.annotation.ExistReview;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewExistValidator implements ConstraintValidator<ExistReview, Long> {

    private final UserQueryService userQueryService;

    @Override
    public void initialize(ExistReview constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<Review> target = userQueryService.findById(value);

        if (target.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.REVIEW_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}