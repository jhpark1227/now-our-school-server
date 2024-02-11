package com.example.school.user.repository;


import com.example.school.domain.Review;
import com.example.school.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    List<ReviewImage> findByReview(Review review);
    List<ReviewImage> findByReviewId(Long reviewId);

}