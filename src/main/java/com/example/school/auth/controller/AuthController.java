package com.example.school.auth.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.auth.service.AuthQueryService;
import com.example.school.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthQueryService authQueryService;

    // 회원가입
    @PostMapping(value = "/register")
    public ApiResponse<Member> register(@RequestBody AuthRequestDTO.RegisterReqDTO registerReqDTO) {
        return ApiResponse.onSuccess(authQueryService.register(registerReqDTO));
    }

    @PostMapping(value = "/login")
    public ApiResponse<AuthResponseDTO.LoginResDTO> login(@RequestBody AuthRequestDTO.LoginReqDTO user) {
        return ApiResponse.onSuccess(authQueryService.login(user));
    }
}
