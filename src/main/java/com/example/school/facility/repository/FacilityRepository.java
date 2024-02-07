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
    List<Facility> findBySchoolAndTagIsNotNull(School school);

    @Query("select f from Facility f left join fetch f.building left join fetch f.reviewList where f.id=:id")
    Optional<Facility> findByIdWithBuildingAndReview(@Param("id") Long id);

    List<Facility> findByKeywordAndSchool(FacilityKeyword keyword, School school);

    @Query("select f from Facility f where f.name like concat('%', :keyword, '%')and f.school=:school")
    List<Facility> findByNameLikeAndSchool(@Param("keyword") String keyword, @Param("school") School school);
}
