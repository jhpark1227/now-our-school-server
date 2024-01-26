package com.example.school.auth.service;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthQueryService {

    Member register(AuthRequestDTO.RegisterReqDTO request);

    Boolean checkUserIdFormat(String id);

    Boolean checkEmailFormat(String email);

    Boolean checkPassword(String password);

    Boolean checkNicknameDuplicate(String nickname);

    AuthResponseDTO.LoginResDTO login(AuthRequestDTO.LoginReqDTO request);

    Boolean changePassword(AuthRequestDTO.ChangePasswordReqDTO request);

    void logout(String accessToken);

    Boolean withdrawUser(String accessToken);
}
