package com.example.school.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.school.entity.Member;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByNickname(String nickname);
}
