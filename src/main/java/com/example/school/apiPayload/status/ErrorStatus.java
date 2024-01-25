package com.example.school.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    BAD_REQUEST(HttpStatus.OK,"COMMON400","잘못된 요청입니다."),
    PAGE_LT_ONE(HttpStatus.BAD_REQUEST,"COMMON401","잘못된 페이지입니다."),
    EXPIRED_JWT(HttpStatus.OK, "3000", "만료된 토큰입니다."),
    BAD_JWT(HttpStatus.OK, "2000", "JWT 토큰이 잘못되었습니다."),



    // 멤버 관려 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    FACILITY_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE_4001","시설이 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_4001","리뷰가 없습니다."),

    // 인증, 인가 관련 에러
    USER_ID_ERROR(HttpStatus.BAD_REQUEST,"AUTH_4002","사용 불가능한 아이디입니다."),
    NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST,"AUTH_4003", "사용 불가능한 닉네임입니다."),
    PASSWORD_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "AUTH_4004", "사용 불가능한 비밀번호입니다."),
    REDIS_ERROR(HttpStatus.BAD_REQUEST, "AUTH_4005", "Redis 오류."),
    EMAIL_ERROR(HttpStatus.BAD_REQUEST,"AUTH_4006", "이메일 인증 실패"),
    EMAIL_CODE_ERROR(HttpStatus.BAD_REQUEST,"AUTH_4007", "이메일 인증번호 불일치");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
