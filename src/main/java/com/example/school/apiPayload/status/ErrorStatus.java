package com.example.school.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    BAD_REQUEST(HttpStatus.OK,"COMMON400","잘못된 요청입니다."),
    EXPIRED_JWT(HttpStatus.OK, "3000", "만료된 토큰입니다."),
    BAD_JWT(HttpStatus.OK, "2000", "JWT 토큰이 잘못되었습니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
