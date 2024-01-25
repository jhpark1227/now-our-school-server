package com.example.school.facility.repository;

import com.example.school.domain.Facility;
import com.example.school.domain.School;
import com.example.school.domain.enums.FacilityKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByBuildingSchoolAndTagIsNotNull(School school);

    @Query("select f from Facility f join fetch f.building join fetch f.reviewList where f.id=:id")
    Optional<Facility> findByIdWithBuildingAndReview(@Param("id") Long id);

    List<Facility> findByKeywordAndBuildingSchool(FacilityKeyword keyword, School school);
}
