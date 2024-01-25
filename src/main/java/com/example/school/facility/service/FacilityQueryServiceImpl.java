package com.example.school.facility.service;

import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.Facility;
import com.example.school.domain.FacilityImage;
import com.example.school.domain.Member;
import com.example.school.domain.Review;
import com.example.school.domain.enums.FacilityKeyword;
import com.example.school.facility.dto.FacilityResponseDTO;
import com.example.school.facility.dto.FacilitySaveResponseDTO;
import com.example.school.facility.repository.FacilityImageRepository;
import com.example.school.facility.repository.FacilityRepository;
import com.example.school.user.repository.ReviewRepository;
import com.example.school.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FacilityQueryServiceImpl implements FacilityQueryService{
    private final FacilityRepository facilityRepository;
    private final ReviewRepository reviewRepository;
    private final FacilityImageRepository facilityImageRepository;
    private final UserRepository userRepository;

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

    @Override
    public FacilityResponseDTO.Detail getDetail(Long facilityId) {
        Facility entity = facilityRepository.findByIdWithBuildingAndReview(facilityId)
                .orElseThrow(()->new GeneralException(ErrorStatus.FACILITY_NOT_FOUND));

        return new FacilityResponseDTO.Detail(entity);
    }

    @Override
    public FacilityResponseDTO.Images getImages(Long facilityId, Integer page) {
        Pageable pageRequest = PageRequest.of(page-1,5);
        Page<FacilityImage> entities = facilityImageRepository.findByFacilityId(facilityId,pageRequest);

        List<String> list = entities.stream().map(entity->entity.getImageURL()).collect(Collectors.toList());

        return new FacilityResponseDTO.Images(
                list,
                entities.getSize(),
                entities.getTotalPages(),
                entities.getTotalElements(),
                entities.isFirst(),
                entities.isLast()
        );
    }

    @Override
    public FacilityResponseDTO.ListByKeyword getListByKeyword(String userId, String keyword) {
        Member member = userRepository.findByUserId(userId)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        FacilityKeyword keywordEnum = FacilityKeyword.valueOf(keyword.toUpperCase());

        List<Facility> entities = facilityRepository.findByKeywordAndBuildingSchool(keywordEnum,member.getSchool());

        List<FacilityResponseDTO.FacilityInKeyword> list = entities.stream().map(entity->{
            return new FacilityResponseDTO.FacilityInKeyword(entity.getId(),entity.getName(), entity.getDescription(), entity.getImageURL());
        }).collect(Collectors.toList());

        return new FacilityResponseDTO.ListByKeyword(list,list.size());
    }
}
