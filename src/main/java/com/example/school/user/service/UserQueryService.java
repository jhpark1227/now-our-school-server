package com.example.school.user.service;

import com.example.school.entity.Member;
import com.example.school.entity.Review;
import com.example.school.user.dto.UserResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserQueryService {

    Optional<Member> findMember(Long id);
    Page<Review> getReviewList(Long MemberId, Integer page);
    Page<Review> findByFacility(Long facilityId, Integer page);
    Page<Review> getAllReviewList(Integer page);

    Optional<Review> findById(Long id);

    UserResponseDTO.Info getInfo(Long id);
}