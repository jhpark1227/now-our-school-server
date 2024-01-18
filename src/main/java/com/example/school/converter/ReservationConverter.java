package com.example.school.converter;

import com.example.school.domain.Reservation;
import com.example.school.reservation.dto.ReservationRequestDTO;
import com.example.school.reservation.dto.ReservationResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationConverter {
    public static ReservationResponseDTO.DetailDTO detailResultDTO(Reservation reservation){
        return ReservationResponseDTO.DetailDTO.builder()
                .month(reservation.getMonth())
                .day(reservation.getDay())
                .duration(reservation.getDuration())
                .facility(reservation.getFacility())
                .build();
    }
    public static ReservationResponseDTO.DetailResultDTO detailResultListDTO(Page<Reservation> reservationList){
        List<ReservationResponseDTO.DetailDTO> reservationDTO = reservationList.stream()
                .map(ReservationConverter::detailResultDTO).collect(Collectors.toList());
        return ReservationResponseDTO.DetailResultDTO.builder()
                .resultList(reservationDTO)
                .isFirst(reservationList.isFirst())
                .isLast(reservationList.isLast())
                .totalPage(reservationList.getTotalPages())
                .totalElements(reservationList.getTotalElements())
                .listSize(reservationDTO.size())
                .build();
    }
}
