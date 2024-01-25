package com.example.school.facility.service;

import com.example.school.domain.Facility;
import com.example.school.domain.Review;
import com.example.school.facility.dto.FacilityResponseDTO;
import com.example.school.facility.dto.FacilitySaveResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface FacilityQueryService {

    Optional<Facility> findFacility(Long id);
    Facility createFacility (Long RegionId, FacilitySaveResponseDTO.CreateFacilityResultDTO request);
    Page<Review> getReviewList(Long FacilityId, Integer page);
    FacilityResponseDTO.Detail getDetail(Long facilityId);

    FacilityResponseDTO.Images getImages(Long facilityId, Integer page);
}