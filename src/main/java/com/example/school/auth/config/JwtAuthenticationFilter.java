package com.example.school.auth.config;

import com.example.school.auth.config.util.JwtUtils;
import com.example.school.auth.config.util.RedisUtils;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        String token = jwtUtils.resolveToken(request.getHeader("Authorization"));
        log.info("token : " + token);

        if (token != null && !token.isEmpty()) {
            try {
                jwtUtils.parseToken(token);
                if (!request.getRequestURI().equals("/api/reissue")) {
                        String isLogout = redisUtils.getData(token);
                        if (isLogout == null) {
                            log.info("enter the this logic");
                            Authentication authentication = jwtUtils.getAuthentication(token);
                            log.info("filter get Authorities() : " + authentication.getAuthorities().toString());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                        throw new RuntimeException("허가받지 않은 토큰입니다.");
                    }
                }
            } catch (ExpiredJwtException e) {
                log.info("expired Token");
                String errorMessage = "토큰이 만료되었습니다.";
                request.setAttribute("exception", errorMessage);
            } catch (JwtException e) {
                log.info("invalid token");
                String errorMessage = "유효하지 않은 토큰입니다.";
                request.setAttribute("exception", errorMessage);
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
