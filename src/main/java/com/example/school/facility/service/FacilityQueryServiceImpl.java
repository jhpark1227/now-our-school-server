package com.example.school.facility.service;

import com.example.school.domain.Facility;
import com.example.school.domain.Review;
import com.example.school.facility.dto.FacilitySaveResponseDTO;
import com.example.school.facility.repository.FacilityRepository;
import com.example.school.user.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class FacilityQueryServiceImpl implements FacilityQueryService{
    private final FacilityRepository facilityRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Optional<Facility> findFacility(Long id) {
        return facilityRepository.findById(id);
    }

    @Override
    public Facility createFacility(Long RegionId, FacilitySaveResponseDTO.CreateFacilityResultDTO request) {
        return null;
    }
    @Override
    public Page<Review> getReviewList(Long FacilityId, Integer page) {

        Facility facility = facilityRepository.findById(FacilityId).get();

        Page<Review> FacilityPage = reviewRepository.findAllByFacility(facility, PageRequest.of(page, 10));
        return FacilityPage;
    }

}
