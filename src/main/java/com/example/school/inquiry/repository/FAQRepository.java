package com.example.school.inquiry.repository;

import com.example.school.domain.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQ> findTop4By();
}
