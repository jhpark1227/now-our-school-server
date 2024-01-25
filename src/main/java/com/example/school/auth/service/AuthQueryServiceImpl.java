package com.example.school.auth.service;

import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.auth.config.util.JwtUtils;
import com.example.school.auth.config.util.RedisUtils;
import com.example.school.auth.converter.AuthConverter;
import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.domain.Member;
import com.example.school.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    @Transactional
    public Member register(AuthRequestDTO.RegisterReqDTO registerReqDTO) {

        Member newMember = AuthConverter.toMember(registerReqDTO);
        return userRepository.save(newMember);
    }

    @Override
    public Boolean checkUserIdFormat(String userId) {

        if (userId.length() < 4 || userId.length() > 15) {
            return false;
        }

        Optional<Member> member = userRepository.findByUserId(userId);
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
        Optional<Member> user = userRepository.findByNickname(nickname);
        return user != null; // 이미 존재하는 경우 true, 그렇지 않은 경우 false 반환
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
        Optional<Member> memberOptional = userRepository.findByUserId(request.getUserId());

        // id를 잘못 입력한 경우
        if (memberOptional.isEmpty()) {
            throw new RuntimeException("Id를 정확하게 입력해주세요.");
        }

        Member member = memberOptional.get();

        // 비밀번호를 잘못 입력한 경우
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호를 정확하게 입력해주세요.");
        }

        String accessToken = jwtUtils.createToken(member.getEmail(), JwtUtils.TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:" + member.getEmail());

        if (refreshToken == null) {
            // refreshToken이 존재하지 않는다면 설정해줘야함
            String newRefreshToken = jwtUtils.createToken(member.getEmail(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
            redisUtils.setDataExpire("RT:" + member.getEmail(), newRefreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME_IN_REDIS);
            refreshToken = newRefreshToken;
        }

        return AuthResponseDTO.LoginResDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(JwtUtils.TOKEN_VALID_TIME)
                .build();
    }



    //로그아웃
    @Override
    public void logout(String accessToken) {
        String resolvedToken = jwtUtils.resolveToken(accessToken);

        String email = jwtUtils.getEmailInToken(resolvedToken);
        String data = redisUtils.getData("RT:" + email);
        if(data != null) {
            redisUtils.deleteData("RT:" + email);
        } else {
            throw new GeneralException(ErrorStatus.REFRESHTOKEN_NOT_FOUND);
        }

        redisUtils.setDataExpire(resolvedToken, "logout", jwtUtils.getExpiration(resolvedToken));
    }

    @Override
    public Boolean changePassword(AuthRequestDTO.ChangePasswordReqDTO request) {
        String email = jwtUtils.getEmailInToken(request.getToken());
        Member member = userRepository.findByEmail(email).orElseThrow(() ->
                new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
        );

        if (passwordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
            member.setPassword(passwordEncoder.encode(request.getChangePassword()));
            userRepository.save(member);
            return true;
        } else {
            return false;
        }
    }

}
