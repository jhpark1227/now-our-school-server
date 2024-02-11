package com.example.school.auth.repository;

import com.example.school.domain.Member;
import com.example.school.domain.School;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);
    @Query("select s from School s where s.name like concat('%', :keyword, '%')")
    List<School> findSchoolByName(@Param("keyword") String keyword);
//    Optional<Member> findById(String id);
    Member findByUserId(String userId);
//
//    Optional<Member> existsById(String id);
}
