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
    private final MailService mailService;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    @Transactional
    public Member register(AuthRequestDTO.RegisterReqDTO registerReqDTO) {

        Member newMember = AuthConverter.toMember(registerReqDTO);
        return userRepository.save(newMember);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
        );
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
        if (!userId.matches(regex)) {
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

        if (!email.matches(".+@.*ac\\.kr$")) {
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
        if (data != null) {
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
        //기존 비밀번호와 일치하는지 확인 후 맞을 시 변경
        if (passwordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
            member.setPassword(passwordEncoder.encode(request.getChangePassword()));
            userRepository.save(member);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean findPasswd(AuthRequestDTO.FindPwRequest request) {
        Member member = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        });

        // 유저 아이디와 요청에서 받은 아이디가 일치하는지 확인
        if (!member.getUserId().equals(request.getUserId())) {
            throw new GeneralException(ErrorStatus.USERID_MISMATCH);
        }

        // 인증번호가 일치하는지 확인
        if (!mailService.verifyCertificationCode(request.getEmail(), request.getAuthCode())) {
            throw new GeneralException(ErrorStatus.EMAIL_CODE_ERROR);
        }

        // 변경된 비밀번호를 저장하지 않고 성공 여부를 반환
        return true;
    }

    @Override
    public AuthResponseDTO.ReissueRespDto reissue(String refreshToken) {
        String resolvedToken = jwtUtils.resolveToken(refreshToken);
        String email = jwtUtils.getEmailInToken(resolvedToken);
        String savedRefreshToken = redisUtils.getData("RT:" + email);
//        log.info("savedRefreshToken : "+savedRefreshToken);
//        log.info("RefreshToken : "+resolvedToken);
        if (refreshToken.isEmpty() || !resolvedToken.equals(savedRefreshToken)) {
            throw new GeneralException(ErrorStatus.INVALID_REFRESH_TOKEN);
        } else {
            String newAccessToken = jwtUtils.createToken(email, JwtUtils.TOKEN_VALID_TIME);
            String newRefreshToken = jwtUtils.createToken(email, JwtUtils.REFRESH_TOKEN_VALID_TIME);
            redisUtils.setDataExpire("RT:" + email, newRefreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME_IN_REDIS);
            String getToken = redisUtils.getData("RT:" + email);

            return AuthResponseDTO.ReissueRespDto.builder()
                    .newAccessToken(newAccessToken)
                    .newRefreshToken(newRefreshToken)
                    .accessTokenExpirationTime(JwtUtils.TOKEN_VALID_TIME)
                    .build();
        }
    }
}
