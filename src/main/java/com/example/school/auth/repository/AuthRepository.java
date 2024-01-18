package com.example.school.auth.repository;

import com.example.school.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Member, Long> {
}
