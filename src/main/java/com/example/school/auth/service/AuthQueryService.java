package com.example.school.auth.service;

import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.domain.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuthQueryService {

    Member register(AuthRequestDTO.RegisterReqDTO request, MultipartFile profileImage);
    List<AuthResponseDTO.SchoolResDTO> searchSchool(String schoolName);
    Boolean checkUserIdFormat(String id);
    Member findMemberByEmail(String email);
    AuthResponseDTO.ReissueRespDto reissue(String refreshToken);

    Boolean checkEmailFormat(String email);

    Boolean checkPassword(String password);

    Boolean checkNicknameDuplicate(String nickname);

    AuthResponseDTO.LoginResDTO login(AuthRequestDTO.LoginReqDTO request);

    Boolean changePassword(AuthRequestDTO.ChangePasswordReqDTO request);
    Boolean findPasswd(AuthRequestDTO.FindPwRequest request);


}
