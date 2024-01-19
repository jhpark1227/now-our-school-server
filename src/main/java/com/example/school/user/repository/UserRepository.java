package com.example.school.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.school.domain.Member;
public interface UserRepository extends JpaRepository<Member, Long> {
}
