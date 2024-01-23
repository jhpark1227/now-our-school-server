package com.example.school.user.service;

import com.example.school.domain.Inquiry;
import com.example.school.domain.Review;
import com.example.school.facility.repository.FacilityRepository;
import com.example.school.user.converter.UserConverter;
import com.example.school.user.dto.UserRequestDTO;
import com.example.school.user.repository.InquiryRepository;
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
    private  final InquiryRepository inquiryRepository;

    @Override
    public Review createReview(Long memberId, Long facilityId, UserRequestDTO.ReviewDTO request) {

        Review review = UserConverter.toReview(request);

        review.setMember(userRepository.findById(memberId).get());
        review.setFacility(facilityRepository.findById(facilityId).get());

        return reviewRepository.save(review);
    }
    @Override
    public Review updateReview(Long memberId, Long facilityId, Long reviewId, UserRequestDTO.ReviewDTO request) {

        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));

        validateReviewOwnership(existingReview, memberId, facilityId);

        existingReview.setTitle(request.getTitle());
        existingReview.setScore(request.getScore());
        existingReview.setBody(request.getBody());

        return reviewRepository.save(existingReview);
    }

    private void validateReviewOwnership(Review review, Long memberId, Long facilityId) {
        if (!review.getMember().getId().equals(memberId) || !review.getFacility().getId().equals(facilityId)) {
            throw new RuntimeException("Review does not belong to the specified member and facility.");
        }
    }

    @Override
    public void deleteReview(Long memberId, Long facilityId, Long reviewId) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        validateReviewOwnership(existingReview, memberId, facilityId);

        reviewRepository.delete(existingReview);
    }

    @Override
    public Inquiry createInquiry(Long memberId, UserRequestDTO.InquiryDTO request) {


        Inquiry inquiry = UserConverter.toInquiry(request);

        inquiry.setMember(userRepository.findById(memberId).get());

        return inquiryRepository.save(inquiry);
    }
  }
