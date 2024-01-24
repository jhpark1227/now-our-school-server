package com.example.school.facility.converter;

import com.example.school.domain.Facility;
import com.example.school.domain.Reservation;
import com.example.school.facility.dto.FacilityResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class FacilityConverter {
    public static FacilityResponseDTO.DetailDTO detailDTO(Facility facility){
        return FacilityResponseDTO.DetailDTO.builder()
                .name(facility.getName())
                .imageURL(facility.getImageURL())
                .time(facility.getTime())
                .location(facility.getLocation())
                .score(facility.getScore())
                .build();
    }

    public static FacilityResponseDTO.DetailResultDTO detailResultDTO(List<Facility> facilityList){
        List<FacilityResponseDTO.DetailDTO> facilityDTO = facilityList.stream()
                .map(FacilityConverter::detailDTO).collect(Collectors.toList());
        return FacilityResponseDTO.DetailResultDTO.builder()
                .resultList(facilityDTO)
                .listSize(facilityList.size())
                .build();

    }
}
