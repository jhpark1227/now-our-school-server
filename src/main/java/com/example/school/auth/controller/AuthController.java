package com.example.school.auth.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.auth.service.AuthQueryService;
import com.example.school.auth.service.MailService;
import com.example.school.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthQueryService authQueryService;
    private final MailService mailService;

    // 회원가입
    @PostMapping(value = "/register")
    public ApiResponse<Member> register(@RequestBody AuthRequestDTO.RegisterReqDTO registerReqDTO) {

        if (authQueryService.checkUserIdFormat(registerReqDTO.getUserId())) {
            return ApiResponse.onFailure(ErrorStatus.USER_ID_ERROR.getCode(), ErrorStatus.USER_ID_ERROR.getMessage());
        }

        if (!authQueryService.checkPassword((registerReqDTO.getPassword()))) {
            return ApiResponse.onFailure(ErrorStatus.PASSWORD_FORMAT_ERROR.getCode(), ErrorStatus.PASSWORD_FORMAT_ERROR.getMessage());
        }

        if (!authQueryService.checkEmailFormat(registerReqDTO.getEmail())) {
            return ApiResponse.onFailure(ErrorStatus.EMAIL_FORMAT_ERROR.getCode(), ErrorStatus.EMAIL_FORMAT_ERROR.getMessage());
        }

        if (!authQueryService.checkNicknameDuplicate(registerReqDTO.getNickname())) {
            return ApiResponse.onFailure(ErrorStatus.NICKNAME_DUPLICATE.getCode(), ErrorStatus.NICKNAME_DUPLICATE.getMessage());
        }
        return ApiResponse.onSuccess(authQueryService.register(registerReqDTO));
    }

    // 로그인
    @PostMapping(value = "/login")
    public ApiResponse<AuthResponseDTO.LoginResDTO> login(@RequestBody AuthRequestDTO.LoginReqDTO user) {
        return ApiResponse.onSuccess(authQueryService.login(user));
    }
    //로그아웃
    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestHeader("Authorization") String accessToken) {
        authQueryService.logout(accessToken);
        return ApiResponse.onSuccess("로그아웃 처리 되었습니다.");
    }

    // 이메일 전송
    @PostMapping(value = "/email-send")
    public ApiResponse<String> sendEmail(@RequestBody AuthRequestDTO.EmailAuthReqDTO emailAuthReqDTO) {

        if (!authQueryService.checkEmailFormat(emailAuthReqDTO.getEmail())) {
            return ApiResponse.onFailure(ErrorStatus.EMAIL_FORMAT_ERROR.getCode(), ErrorStatus.EMAIL_FORMAT_ERROR.getMessage());
        }
        String verificationCode = mailService.sendCertificationMail(emailAuthReqDTO.getEmail());

        return ApiResponse.onSuccess(verificationCode);
    }
    // 이메일 인증
    @PostMapping(value = "/email-auth")
    public ApiResponse<String> authEmail(@RequestBody AuthRequestDTO.EmailAuthReqDTO emailAuthReqDTO) {
        Boolean isAuthenticationSuccessful = mailService.verifyCertificationCode(emailAuthReqDTO.getEmail(), emailAuthReqDTO.getAuthCode());

        if (isAuthenticationSuccessful) {
            // 인증이 성공한 경우
            return ApiResponse.onSuccess("이메일 인증 성공!");
        } else {
            // 인증이 실패한 경우
            return ApiResponse.onFailure(ErrorStatus.EMAIL_CODE_ERROR.getCode(), ErrorStatus.EMAIL_CODE_ERROR.getMessage());
        }
    }

    // 비밀번호 변경
    @PostMapping(value = "/change-password")
    public ApiResponse<String> changePassword(@RequestBody AuthRequestDTO.ChangePasswordReqDTO changePasswordReqDTO) {

        // 비밀번호 변경 수행
        authQueryService.changePassword(changePasswordReqDTO);

        return ApiResponse.onSuccess("비밀번호가 성공적으로 변경되었습니다.");
    }


}
