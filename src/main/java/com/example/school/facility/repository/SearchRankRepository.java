package com.example.school.facility.repository;

import com.example.school.entity.School;
import com.example.school.entity.SearchRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRankRepository extends JpaRepository<SearchRank, Long> {
    void deleteBySchool(School school);

    List<SearchRank> findBySchool(School school);
}
