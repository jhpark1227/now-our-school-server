package com.example.school.auth.controller;

import com.example.school.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
}