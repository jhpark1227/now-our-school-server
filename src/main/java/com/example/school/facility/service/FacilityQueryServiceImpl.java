package com.example.school.facility.service;

import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.*;
import com.example.school.domain.enums.FacilityKeyword;
import com.example.school.facility.dto.FacilityResponseDTO;
import com.example.school.facility.dto.FacilitySaveResponseDTO;
import com.example.school.facility.repository.BuildingRepository;
import com.example.school.facility.repository.FacilityImageRepository;
import com.example.school.facility.repository.FacilityRepository;
import com.example.school.facility.repository.SearchRankRepository;
import com.example.school.user.repository.ReviewRepository;
import com.example.school.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FacilityQueryServiceImpl implements FacilityQueryService{
    private final FacilityRepository facilityRepository;
    private final ReviewRepository reviewRepository;
    private final FacilityImageRepository facilityImageRepository;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;
    private final SearchRankRepository searchRankRepository;
    private final FacilityService facilityService;
    private final RedisTemplate redisTemplate;

    @Override
    public Optional<Facility> findFacility(Long id) {
        return facilityRepository.findById(id);
    }

    @Override
    public Facility createFacility(Long RegionId, FacilitySaveResponseDTO.CreateFacilityResultDTO request) {
        return null;
    }
    /*
    @Override
    public Page<Review> getReviewList(Long FacilityId, Integer page) {

        Facility facility = facilityRepository.findById(FacilityId).get();

        Page<Review> FacilityPage = reviewRepository.findAllByFacility(facility, PageRequest.of(page, 10));
        return FacilityPage;
    }
*/
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
    public FacilityResponseDTO.ListByKeyword getListByKeyword(Long memberId, String keyword) {
        Member member = userRepository.findById(memberId)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        FacilityKeyword keywordEnum = FacilityKeyword.valueOf(keyword.toUpperCase());

        List<Facility> entities = facilityRepository.findByKeywordAndSchool(keywordEnum,member.getSchool());

        List<FacilityResponseDTO.FacilityInKeyword> list = entities.stream().map(entity->{
            return new FacilityResponseDTO.FacilityInKeyword(entity.getId(),entity.getName(), entity.getDescription(), entity.getImageURL());
        }).collect(Collectors.toList());

        return new FacilityResponseDTO.ListByKeyword(list,list.size());
    }

    @Override
    public FacilityResponseDTO.DetailOnMarker getDetailOnMarker(Long buildingId) {
        Building entity = buildingRepository.findByIdWithBuildingHours(buildingId)
                .orElseThrow(()->new GeneralException(ErrorStatus.BUILDING_NOT_FOUND));

        List<FacilityResponseDTO.BuildingHourDTO> hours = entity.getBuildingHours().stream().map(hour->{
            return new FacilityResponseDTO.BuildingHourDTO(hour.getName(), hour.getOpeningTime(), hour.getClosingTime());
        }).collect(Collectors.toList());

        return new FacilityResponseDTO.DetailOnMarker(entity.getName(), entity.getImageURL(), hours);
    }

    @Override
    public FacilityResponseDTO.SearchResults searchFacility(Long memberId, String keyword) {
        Member member = userRepository.findById(memberId)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        List<Facility> entities = facilityRepository.findByNameLikeAndSchool(keyword.trim(),member.getSchool());
        facilityService.saveSearchLog(memberId, member.getSchool().getId(), keyword);

        List<FacilityResponseDTO.SearchResult> list = entities.stream().map(entity->{
            return new FacilityResponseDTO.SearchResult(entity.getId(),entity.getName(),entity.getImageURL(),entity.getTime(),entity.getBuilding().getName());
        }).collect(Collectors.toList());

        return new FacilityResponseDTO.SearchResults(list,list.size());
    }

    @Override
    public FacilityResponseDTO.SearchLogList getSearchLog(Long memberId) {
        String key = facilityService.searchLogKey(memberId);
        Set<String> set = redisTemplate.opsForZSet().reverseRange(key,0,9);
        List<String> list = set.stream().collect(Collectors.toList());

        return new FacilityResponseDTO.SearchLogList(list, list.size());
    }

    @Override
    public FacilityResponseDTO.SearchRankList getSearchRank(Long memberId) {
        Member member = userRepository.findById(memberId)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        List<SearchRank> entities = searchRankRepository.findBySchool(member.getSchool());

        return new FacilityResponseDTO.SearchRankList(entities);
    }
}
