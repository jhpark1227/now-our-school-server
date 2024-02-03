package com.example.school.facility.repository;

import com.example.school.entity.Facility;
import com.example.school.entity.School;
import com.example.school.entity.enums.FacilityKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findByBuildingSchoolAndTagIsNotNull(School school);

    @Query("select f from Facility f left join fetch f.building left join fetch f.reviewList where f.id=:id")
    Optional<Facility> findByIdWithBuildingAndReview(@Param("id") Long id);

    List<Facility> findByKeywordAndBuildingSchool(FacilityKeyword keyword, School school);

    @Query("select f from Facility f left join fetch f.building b where f.name like concat('%', :keyword, '%')and b.school=:school")
    List<Facility> findByNameLikeAndBuildingSchool(@Param("keyword") String keyword, @Param("school") School school);
}
