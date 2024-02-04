package com.example.school.auth.service;

import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.auth.config.util.JwtUtils;
import com.example.school.auth.config.util.RedisUtils;
import com.example.school.auth.converter.AuthConverter;
import com.example.school.auth.dto.AuthRequestDTO;
import com.example.school.auth.dto.AuthResponseDTO;
import com.example.school.auth.repository.AuthRepository;
import com.example.school.awsS3.AwsS3Service;
import com.example.school.entity.Member;
import com.example.school.entity.School;
import com.example.school.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthQueryServiceImpl implements AuthQueryService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;
    private final AwsS3Service awsS3Service;

    //    @Override
//    @Transactional
//    public Member register(AuthRequestDTO.RegisterReqDTO registerReqDTO) {
//
//        Member newMember = AuthConverter.toMember(registerReqDTO);
//        return userRepository.save(newMember);
//    }
    @Override
    @Transactional
    public Member register(AuthRequestDTO.RegisterReqDTO registerReqDTO, MultipartFile profileImage) {
        // 파일 업로드
        String imageUrl = awsS3Service.uploadSingleFile(profileImage);

        // 회원 정보 생성
        Member newMember = AuthConverter.toMember(registerReqDTO);
        newMember.setProfileImg(imageUrl);

        // 회원 정보 저장
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
    public Boolean checkIdentifyNumFormat(String identifyNum) {
        if (identifyNum.length() != 7) {
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
    public Boolean validateDuplicateEmail(String email) {
        Optional<Member> member = userRepository.findByEmail(email);
        if (!member.isEmpty()) {
            return true; // 존재한다면 true 반환
        } else {
            return false; // 존재하지 않으면 false 반환
        }
    }

    @Override
    public Boolean validateDuplicateUserId(String userId) {
        Optional<Member> member = userRepository.findByUserId(userId);
        if (!member.isEmpty()) {
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
            throw new GeneralException(ErrorStatus.USER_ID_ERROR);
        }

        Member member = memberOptional.get();

        // 비밀번호를 잘못 입력한 경우
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_ERROR);
        }

        String accessToken = jwtUtils.createToken(member.getEmail(), JwtUtils.TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:" + member.getEmail());

        if (refreshToken == null) {
            // refreshToken이 존재하지 않는다면 설정해줘야함
            String newRefreshToken = jwtUtils.createToken(member.getEmail(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
            redisUtils.setDataExpire("RT:" + member.getEmail(), newRefreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME_IN_REDIS);
            refreshToken = newRefreshToken;
        }
        String userid = member.getUserId();

        return AuthResponseDTO.LoginResDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userid(userid)
                .memberId(member.getId())
                .accessTokenExpirationTime(JwtUtils.TOKEN_VALID_TIME)
                .build();
    }

    @Override
    @Transactional
    public Boolean changePassword(AuthRequestDTO.ChangePasswordReqDTO request) {
        String email = jwtUtils.getEmailInToken(request.getToken());
        Member member = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        });
        //기존 비밀번호와 일치하는지 확인 후 맞을 시 변경
        if (passwordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
            member.changePassword(passwordEncoder.encode(request.getChangePassword()));
            userRepository.save(member);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
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
//        String encryptedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        member.changePassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(member);

        if (passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            System.out.println("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            System.out.println("비밀번호 변경 실패");
            return false;
        }

        return true;
    }

//    @Override
//    public Boolean changePwd(AuthRequestDTO.FindPwRequest request) {
//        // 토큰에서 이메일을 추출하지 않고 직접 request에서 얻어옴
//        String email = request.getPassword(); // 예시로 request에 getEmail() 메서드가 있다고 가정
//
//        Member member = userRepository.findByEmail(email).orElseThrow(() -> {
//            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
//        });
//
//        member.changePassword(passwordEncoder.encode(request.getPassword()));
//        userRepository.save(member);
//        return true;
//    }

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

    public List<AuthResponseDTO.SchoolResDTO> searchSchool(String schoolName) {
        // 대학 검색 쿼리 수행
        List<School> schools = authRepository.findSchoolByName(schoolName);

        // 검색된 대학이 없을 경우
        if (schools.isEmpty()) {
            // 또는 다른 처리 로직을 수행하거나 예외를 던질 수 있습니다.
            return Collections.emptyList();
        }

        // 검색된 대학들을 DTO로 매핑
        List<AuthResponseDTO.SchoolResDTO> schoolResDTOs = schools.stream()
                .map(school -> new AuthResponseDTO.SchoolResDTO(school.getId(), school.getName()))
                .collect(Collectors.toList());

        // 최종 결과 반환
        return schoolResDTOs;
    }
}
