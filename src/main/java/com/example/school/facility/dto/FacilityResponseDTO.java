package com.example.school.facility.dto;

import com.example.school.reservation.dto.ReservationResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FacilityResponseDTO {
    //사용자 이용한 시설물 정보 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailDTO{
        Long id;
        String name;
        String imageURL;
        String time;
        String location;
        Float score;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailResultDTO{
        List<FacilityResponseDTO.DetailDTO> resultList;
        Integer listSize;
    }
}
