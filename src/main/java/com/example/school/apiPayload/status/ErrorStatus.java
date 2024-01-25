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
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_4001","리뷰가 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
