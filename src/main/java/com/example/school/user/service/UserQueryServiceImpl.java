package com.example.school.user.service;

import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.Facility;
import com.example.school.domain.Member;
import com.example.school.domain.Review;
import com.example.school.facility.repository.FacilityRepository;
import com.example.school.user.dto.UserResponseDTO;
import com.example.school.user.repository.ReviewRepository;
import com.example.school.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService{
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final FacilityRepository facilityRepository;

    @Override
    public Optional<Member> findMember(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<Review> getReviewList(Long MemberId, Integer page) {

        Member member = userRepository.findById(MemberId).get();

        Page<Review>MemberPage = reviewRepository.findAllByMember(member, PageRequest.of(page, 10));
        return MemberPage;
    }

    @Override
    public Page<Review> findByFacility(Long facilityId, Integer page) {
        Facility facility = facilityRepository.findById(facilityId).get();
        Page<Review> reviews = reviewRepository.findAllByFacility(facility, PageRequest.of(page-1, 10));

        return reviews;
    }

    @Override
    public Optional<Review> findById(Long id) {
        {
            return reviewRepository.findById(id);
        }
    }

    @Override
    public UserResponseDTO.Info getInfo(Long id) {
        Member member = userRepository.findById(id)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        return new UserResponseDTO.Info(member);
    }
}
