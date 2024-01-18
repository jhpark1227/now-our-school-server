package com.example.school.auth.service;

import com.example.school.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
}
