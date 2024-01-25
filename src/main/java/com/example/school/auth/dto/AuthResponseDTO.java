package com.example.school.auth.dto;

import lombok.*;

public class AuthResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RegisterResDTO {
        String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginResDTO {
        String accessToken;
        String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ForgotPasswordResDTO {
        boolean isSuccess;
        int code;
        String message;
        String result;
    }
}