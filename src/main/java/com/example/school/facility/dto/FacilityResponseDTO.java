package com.example.school.facility.dto;

import com.example.school.domain.Building;
import com.example.school.domain.Facility;
import com.example.school.domain.SearchRank;
import com.example.school.domain.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FacilityResponseDTO {
    //사용자 이용한 시설물 정보 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailDTO{
        Long facilityId;
        String name;
        String imageURL;
        String time;
        String location;
        String purpose;
        Float score;
        String year;
        String month;
        String day;
        Integer startTime;
        Integer endTime;
        Integer duration;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailResultDTO{
        List<DetailDTO> resultList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;    }

    @Getter @AllArgsConstructor
    public static class Categories{
        List<CategoryWithFacilities> categories;
        int count;
    }

    @Getter @AllArgsConstructor
    public static class CategoryWithFacilities{
        String name;
        List<FacilityIdAndName> facilities;
        int count;

        public CategoryWithFacilities(Theme theme){
            name = theme.getName();
            facilities = theme.getFacilities().stream().map(fac->new FacilityIdAndName(fac.getId(),fac.getName()))
                    .collect(Collectors.toList());
            count = facilities.size();
        }
        public CategoryWithFacilities(Building theme){
            name = theme.getName();
            facilities = theme.getFacilities().stream().map(fac->new FacilityIdAndName(fac.getId(),fac.getName()))
                    .collect(Collectors.toList());
            count = facilities.size();
        }
    }

    @Getter @AllArgsConstructor
    public static class FacilityIdAndName{
        Long id;
        String name;
    }

    @Getter @AllArgsConstructor
    public static class Markers{
        List<Marker> list;
        int count;
    }

    @Getter @AllArgsConstructor
    public static class Marker{
        Long id;
        Double latitude;
        Double longitude;
    }

    @Getter @AllArgsConstructor
    public static class Tags{
        List<Tag> tags;
    }

    @Getter @AllArgsConstructor
    public static class Tag{
        String tag;
        List<FacilityWithTag> facilities;
        int count;
    }

    @Getter @AllArgsConstructor
    public static class FacilityWithTag{
        Long id;
        String name;
        String imageURL;
    }

    @Getter
    public static class Detail{
        Long id;
        String name;
        Float score;
        String location;
        String purpose;
        String item;
        String time;
        String caution;
        String buildingName;
        Double latitude;
        Double longitude;
        List<ReviewInDetail> reviews;
        int reviewCount;

        public Detail(Facility facility){
            id = facility.getId();
            name = facility.getName();
            score = facility.getScore();
            location = facility.getLocation();
            purpose = facility.getPurpose();
            item = facility.getItem();
            time = facility.getTime();
            caution = facility.getCaution();
            buildingName = facility.getBuilding().getName();
            latitude = facility.getBuilding().getLatitude();
            longitude = facility.getBuilding().getLongitude();
            reviews = facility.getReviewList().stream().map(review->{
                return new ReviewInDetail(
                        review.getId(),
                        review.getScore(),
                        review.getCreatedAt().toLocalDate(),
                        review.getMember().getNickname(),
                        review.getMember().getProfileImg(),
                        review.getBody());
            }).collect(Collectors.toList());
            reviewCount = reviews.size();
        }
    }

    @Getter @AllArgsConstructor
    public static class ReviewInDetail{
        Long id;
        Float score;
        LocalDate date;
        String nickname;
        String imageURL;
        String body;
    }

    @Getter @AllArgsConstructor
    public static class Images {
        List<String> list;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Getter @AllArgsConstructor
    public static class ListByKeyword{
        List<FacilityInKeyword> list;
        int count;
    }

    @Getter @AllArgsConstructor
    public static class FacilityInKeyword{
        Long id;
        String name;
        String description;
        String imageURL;
    }

    @Getter @AllArgsConstructor
    public static class DetailOnMarker{
        String name;
        String imageURL;
        List<BuildingHourDTO> hours;
    }

    @Getter @AllArgsConstructor
    public static class BuildingHourDTO{
        String name;
        String openingTime;
        String closingTime;
    }

    @Getter @AllArgsConstructor
    public static class SearchResults{
        List<SearchResult> list;
        int count;
    }

    @Getter @AllArgsConstructor
    public static class SearchResult{
        Long id;
        String name;
        String imageURL;
        String time;
        String buildingName;
    }

    @Getter @AllArgsConstructor
    public static class LibraryStatus{
        List<LibraryDetail> list;
    }

    @Getter @Builder
    public static class LibraryDetail{
        Integer current;
        String name;
        String status;
        Integer total;
    }

    @Getter @AllArgsConstructor
    public static class SearchLogList {
        List<String> list;
        int count;
    }

    @Getter @AllArgsConstructor
    public static class DeleteSearchLog {
        String value;
    }

    @Getter
    public static class SearchRankList {
        List<SearchRankDTO> list;
        int count;

        public SearchRankList(List<SearchRank> entities){
            list = entities.stream().map(entity->{
                return new SearchRankDTO(entity.getRanking(), entity.getValue());
            }).collect(Collectors.toList());
            count = list.size();
        }
    }

    @Getter @AllArgsConstructor
    public static class SearchRankDTO{
        int ranking;
        String value;
    }
}

