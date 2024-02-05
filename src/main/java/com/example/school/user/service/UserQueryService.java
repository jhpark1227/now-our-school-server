package com.example.school.user.service;

import com.example.school.domain.Member;
import com.example.school.domain.Review;
import com.example.school.user.dto.UserResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserQueryService {

    Optional<Member> findMember(Long id);
    Page<UserResponseDTO.ReviewPreViewDTO>  getReviewList(Long MemberId, Integer page);
    Page<UserResponseDTO.ReviewPreViewDTO> findByFacility(Long facilityId, Integer page);
    Page<UserResponseDTO.ReviewPreViewDTO> getAllReviewList(Integer page);

    Optional<Review> findById(Long id);

    UserResponseDTO.Info getInfo(Long id);
}