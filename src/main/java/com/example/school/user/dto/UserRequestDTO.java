package com.example.school.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UserRequestDTO {
    @Getter
    public static class ReviewDTO{
        @NotBlank
        String title;
        @NotNull
        Float point;
        @NotBlank
        String content;
    }
}
