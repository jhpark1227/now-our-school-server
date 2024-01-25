package com.example.school.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthRequestDTO {

    @Getter @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    //회원 가입
    public static class RegisterReqDTO {
        @NotBlank(message = "이름을 입력해 주세요.")
        String name;
        @NotBlank(message = "이메일을 입력해 주세요.")
        String email;
        @NotBlank(message = "비밀번호를 입력해 주세요.")
        String password;
        @NotBlank(message = "주민번호를 입력해 주세요.")
        String identify_num;
        @NotBlank(message = "핸드폰번호를 입력해 주세요.")
        String phone;
        @NotBlank(message = "아이디를 입력해 주세요.")
        String userId;
        String nickname;

        Integer age;
        Integer grade;
        String school;
    }

    @Getter @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginReqDTO {

        String userId;
        String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(this.userId, this.password);
        }
    }

    @Getter @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailAuthReqDTO {

        String email;
        String authCode;
    }

    @Getter @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangePasswordReqDTO {
        @NotBlank(message = "현재 비밀번호를 입력해 주세요.")
        String currentPassword;

        @NotBlank(message = "새로운 비밀번호를 입력해 주세요.")
        String newPassword;

        public String getCurrentPassword() {
            return currentPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }
    }
}
