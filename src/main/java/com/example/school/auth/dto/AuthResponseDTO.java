package com.example.school.auth.dto;

import lombok.*;

import java.time.LocalDateTime;

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
        String userid;
        private Long accessTokenExpirationTime;

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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class findUserIdDTO {
        String userId;
        LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReissueRespDto {
        private String newAccessToken;
        private String newRefreshToken;
        private Long accessTokenExpirationTime;
    }
}