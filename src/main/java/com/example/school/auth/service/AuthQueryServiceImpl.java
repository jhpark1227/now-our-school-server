package com.example.school.auth.service;

import com.example.school.auth.config.util.JwtUtils;
import com.example.school.auth.config.util.RedisUtils;
import com.example.school.auth.converter.AuthConverter;
import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.auth.repository.AuthRepository;
import com.example.school.domain.Member;
import lombok.RequiredArgsConstructor;
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
    public Boolean checkUserIdDuplicate(String userId) {
        Member member = authRepository.findByUserId(userId);
        if(member != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean checkNicknameDuplicate(String nickname) {
        Optional<Member> member = authRepository.existsByNickname(nickname);
        if(member.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean checkPassword(String password) {
        return null;
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

//            System.out.println("입력된 비밀번호: " + request.getPassword());
//            System.out.println("저장된 비밀번호: " + member.getPassword());
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
}
