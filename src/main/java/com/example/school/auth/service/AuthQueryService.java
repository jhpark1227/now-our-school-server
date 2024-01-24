package com.example.school.auth.service;

import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.domain.Member;

public interface AuthQueryService {

    Member register(AuthRequestDTO.RegisterReqDTO request);
    Boolean checkUserIdDuplicate(String id);
    Boolean checkPassword(String password);
    Boolean checkNicknameDuplicate(String nickname);

    AuthResponseDTO.LoginResDTO login(AuthRequestDTO.LoginReqDTO request);

}
