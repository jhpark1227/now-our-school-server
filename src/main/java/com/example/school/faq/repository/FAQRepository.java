package com.example.school.faq.repository;

import com.example.school.entity.FAQ;
import com.example.school.entity.enums.FaqType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQ> findTop4By();

    Page<FAQ> findByType(FaqType type, Pageable page);
}
