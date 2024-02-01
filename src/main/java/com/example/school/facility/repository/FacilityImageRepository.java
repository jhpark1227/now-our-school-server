package com.example.school.facility.repository;

import com.example.school.entity.FacilityImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityImageRepository extends JpaRepository<FacilityImage,Long> {
     Page<FacilityImage> findByFacilityId(Long facilityId, Pageable page);
}
