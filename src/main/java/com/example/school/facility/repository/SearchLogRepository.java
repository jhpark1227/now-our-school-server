package com.example.school.facility.repository;

import com.example.school.entity.School;
import com.example.school.entity.SearchLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long> {
    Optional<SearchLog> findByValueAndSchool(String value, School school);

    List<SearchLog> findTop5BySchoolOrderByCountDesc(School school);
}
