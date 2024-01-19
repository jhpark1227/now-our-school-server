package com.example.school.user.service;

import com.example.school.domain.Member;
import com.example.school.domain.Review;
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
public class UserQueryServiceimpl implements UserQueryService{
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

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
}
