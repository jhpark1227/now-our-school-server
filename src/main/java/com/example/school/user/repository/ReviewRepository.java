package com.example.school.user.repository;

import com.example.school.domain.Facility;
import com.example.school.domain.Member;
import com.example.school.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByFacility(Facility facility, PageRequest pageRequest);
    Page<Review> findAllByMember(Member member, PageRequest pageRequest);

}