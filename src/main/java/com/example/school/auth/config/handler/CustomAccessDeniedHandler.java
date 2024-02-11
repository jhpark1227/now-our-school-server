package com.example.school.auth.config.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden 상태 코드 설정
        response.setContentType("application/json;charset=UTF-8");

        // 에러 응답 본문에 메시지 추가
        String errorMessage = "액세스 거부: " + accessDeniedException.getMessage();
        response.getWriter().write(errorMessage);

        // 로깅
        log.info(errorMessage);
    }
}
