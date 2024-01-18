package com.example.school.apiPayload;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(value = GeneralException.class)
    public ApiResponse<Object> onThrowException(GeneralException generalException) {
        return ApiResponse.onFailure(
                generalException.getErrorStatus().getCode(),
                generalException.getErrorStatus().getMessage()
        );
    }
}
