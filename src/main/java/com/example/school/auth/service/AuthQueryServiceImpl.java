package com.example.school.auth.service;

import com.example.school.auth.config.util.JwtUtils;
import com.example.school.auth.config.util.RedisUtils;
import com.example.school.auth.converter.AuthConverter;
import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.auth.repository.AuthRepository;
import com.example.school.domain.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthQueryServiceImpl implements AuthQueryService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    @Transactional
    public Member register(AuthRequestDTO.RegisterReqDTO registerReqDTO) {

        Member newMember = AuthConverter.toMember(registerReqDTO);
        return authRepository.save(newMember);
    }

    @Override
    public Boolean checkUserIdFormat(String userId) {

        if (userId.length() < 4 || userId.length() > 15) {
            return false;
        }

        Member member = authRepository.findByUserId(userId);
        if (member != null) {
            return false;
        }

        String regex = "^[a-zA-Z]+[a-zA-Z0-9]$";
        if(!userId.matches(regex)){
            return false;
        }

        return true;
    }

    @Override
    public Boolean checkNicknameDuplicate(String nickname) {
        Optional<Member> user = authRepository.findByNickname(nickname);
        if (!user.isEmpty()) {
            return true; // 존재한다면 true 반환
        } else {
            return false; // 존재하지 않으면 false 반환
        }
    }

    @Override
    public Boolean checkPassword(String password) {

        // 비밀번호 길이
        if (password.length() < 8 || password.length() > 15) {
            return false;
        }

        // 영문자 대소문자 조합
        if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            return false;
        }


        // 특수문자 확인
//        String specialChars = "[!@#$%&*]";
//        if (!password.matches(".*" + specialChars + ".*")) {
//            return false;
//        }

        return true;
    }

    public String extractEmailAddress(String emailJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(emailJson);
        return jsonNode.get("email").asText();
    }

    @Override
    public Boolean checkEmailFormat(String email) {
        System.out.print(email);

        if(!email.matches(".+@.*ac\\.kr$")){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public AuthResponseDTO.LoginResDTO login(AuthRequestDTO.LoginReqDTO request) {
        Member member = authRepository.findByUserId(request.getUserId());

        // id를 잘못 입력한 경우
        if (member == null) {
            throw new RuntimeException("Id를 정확하게 입력해주세요.");
        }

        // 비밀번호를 잘못 입력한 경우
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new RuntimeException("비밀번호를 정확하게 입력해주세요.");
        }

        String accessToken = jwtUtils.createToken(member.getEmail(), JwtUtils.TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:" + member.getEmail());

        if (refreshToken == null) {
            // refreshToken이 존재하지 않는다면 설정해줘야함
            String newRefreshToken = jwtUtils.createToken(member.getEmail(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
//            log.info("newRefreshToken : "+newRefreshToken);
            redisUtils.setDataExpire("RT:" + member.getEmail(), newRefreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME_IN_REDIS);
            refreshToken = newRefreshToken;

        }

        return AuthResponseDTO.LoginResDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    @Transactional
    public AuthResponseDTO.ForgotPasswordResDTO changePassword(AuthRequestDTO.ChangePasswordReqDTO changePasswordReqDTO) {
        try {
            // 사용자의 현재 인증 정보에서 userId를 가져옵니다.
            String userId = getUserIdFromAuthentication();

            // userId로 사용자 정보를 찾습니다.
            Member member = authRepository.findByUserId(userId);

            // 현재 비밀번호가 일치하는지 확인합니다.
            if (!passwordEncoder.matches(changePasswordReqDTO.getCurrentPassword(), member.getPassword())) {
                return AuthResponseDTO.ForgotPasswordResDTO.builder()
                        .isSuccess(false)
                        .code(400)
                        .message("현재 비밀번호가 일치하지 않습니다.")
                        .result("현재 비밀번호가 일치하지 않습니다.")
                        .build();
            }

            // 새로운 비밀번호가 형식에 맞는지 확인합니다.
            if (!checkPassword(changePasswordReqDTO.getNewPassword())) {
                return AuthResponseDTO.ForgotPasswordResDTO.builder()
                        .isSuccess(false)
                        .code(400)
                        .message("새로운 비밀번호 형식이 유효하지 않습니다.")
                        .result("새로운 비밀번호 형식이 유효하지 않습니다.")
                        .build();
            }

            // 비밀번호를 업데이트하고 저장합니다.
            member.setPassword(passwordEncoder.encode(changePasswordReqDTO.getNewPassword()));
            authRepository.save(member);

            // 성공 응답을 반환합니다.
            return AuthResponseDTO.ForgotPasswordResDTO.builder()
                    .isSuccess(true)
                    .code(200)
                    .message("비밀번호가 변경되었습니다.")
                    .result("비밀번호가 변경되었습니다.")
                    .build();

        } catch (Exception e) {
            // 실패 시 예외 처리
            return AuthResponseDTO.ForgotPasswordResDTO.builder()
                    .isSuccess(false)
                    .code(500)
                    .message("비밀번호 변경에 실패했습니다.")
                    .result("비밀번호 변경에 실패했습니다.")
                    .build();
        }
    }

    private String getUserIdFromAuthentication() {
        // 현재 사용자의 인증 정보에서 userId를 추출하는 로직을 구현합니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // 사용자가 인증되어 있을 때
            return authentication.getName();
        } else {
            // 사용자가 인증되어 있지 않을 때 예외 처리 또는 다른 처리를 수행할 수 있습니다.
            throw new RuntimeException("사용자 인증 정보를 찾을 수 없습니다.");
        }
    }

}
