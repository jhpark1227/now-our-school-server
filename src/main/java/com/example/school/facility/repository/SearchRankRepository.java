package com.example.school.facility.repository;

import com.example.school.domain.School;
import com.example.school.domain.SearchRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRankRepository extends JpaRepository<SearchRank, Long> {
    void deleteBySchool(School school);

    List<SearchRank> findBySchool(School school);
}
