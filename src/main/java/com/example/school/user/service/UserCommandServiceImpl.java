package com.example.school.user.service;

import com.example.school.domain.Review;
import com.example.school.facility.repository.FacilityRepository;
import com.example.school.user.converter.UserConverter;
import com.example.school.user.dto.UserRequestDTO;
import com.example.school.user.repository.ReviewRepository;
import com.example.school.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{

    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(Long memberId, Long facilityId, UserRequestDTO.ReviewDTO request) {

        Review review = UserConverter.toReview(request);

        review.setMember(userRepository.findById(memberId).get());
        review.setFacility(facilityRepository.findById(facilityId).get());

        return reviewRepository.save(review);
    }
}
