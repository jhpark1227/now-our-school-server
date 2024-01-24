package com.example.school.facility.repository;

import com.example.school.domain.Facility;
import com.example.school.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByBuildingSchoolAndTagIsNotNull(School school);
}
