package com.example.school.auth.converter;

import com.example.school.auth.config.util.JwtUtils;
import com.example.school.auth.config.util.RedisUtils;
import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.domain.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthConverter {

    // 로그인
    public static AuthResponseDTO.RegisterResDTO toRegisterResDTO(Member member) {
        return AuthResponseDTO.RegisterResDTO.builder()
                .nickname(member.getNickname())
                .build();
    }

    public static Member toMember(AuthRequestDTO.RegisterReqDTO request) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        return Member.builder()
                .name(request.getName())
                .id(request.getId())
                .email(request.getEmail())
                .identify_num(request.getIdentify_num())
                .password(encryptedPassword)
                .phone(request.getPhone())
                .nickname(request.getNickname())
                .age(request.getAge())
                .build();
    }

//    public static AuthResponseDTO.LoginResDTO toLoginResDTO(){
//        return null;
//    }
}
