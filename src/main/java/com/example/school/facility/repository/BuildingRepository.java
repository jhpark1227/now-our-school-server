package com.example.school.facility.repository;

import com.example.school.domain.Building;
import com.example.school.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    @Query("select b from Building b join fetch b.facilities where b.school=:school")
    List<Building> findBySchoolWithFacility(@Param("school") School school);
}
