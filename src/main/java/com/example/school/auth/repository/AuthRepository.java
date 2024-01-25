package com.example.school.auth.repository;

import com.example.school.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Member, Long> {
    Optional<Member> existsByNickname(String nickname);
    Optional<Member> findByEmail(String email);
//    Optional<Member> findById(String id);
    Member findByUserId(String userId);
//
//    Optional<Member> existsById(String id);
}
