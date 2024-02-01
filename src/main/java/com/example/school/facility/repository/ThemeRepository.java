package com.example.school.facility.repository;

import com.example.school.entity.School;
import com.example.school.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    @Query("select c from Theme c join fetch c.facilities where c.school=:school")
    List<Theme> findBySchoolWithFacility(@Param("school") School school);
}
