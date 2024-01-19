package com.example.school.converter;

import com.example.school.domain.Reservation;
import com.example.school.reservation.dto.ReservationRequestDTO;
import com.example.school.reservation.dto.ReservationResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationConverter {
    //예약 세부내용
    public static ReservationResponseDTO.DetailDTO detailResultDTO(Reservation reservation){
        return ReservationResponseDTO.DetailDTO.builder()
                .month(reservation.getMonth())
                .start_time(reservation.getStart_time())
                .end_time(reservation.getEnd_time())
                .day(reservation.getDay())
                .duration(reservation.getDuration())
                .facility(reservation.getFacility())
                .member(reservation.getMember())
                .build();
    }
    //예약 세부 내용들 list
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
    //예약 불가능한 시간대
    public static ReservationResponseDTO.bookedUpDTO bookedUpDTO(Reservation reservation){
        return ReservationResponseDTO.bookedUpDTO.builder()
                .endTime(reservation.getEnd_time())
                .startTime(reservation.getStart_time())
                .year(reservation.getYear())
                .month(reservation.getMonth())
                .day(reservation.getDay())
                .build();
    }
    //예약 불가능한 시간대 세부내용 리스트
    public static ReservationResponseDTO.bookedUpListDTO bookedUpListDTO(List<Reservation> reservationList){
        List<ReservationResponseDTO.bookedUpDTO> reservationDTO = reservationList.stream()
                .map(ReservationConverter::bookedUpDTO).collect(Collectors.toList());
        return ReservationResponseDTO.bookedUpListDTO.builder()
                .bookedUpList(reservationDTO)
                .build();
    }
    //예약 하기 -> 예약 만듬
    public static Reservation reservation(ReservationRequestDTO.ReservationDTO reservationDTO){
        return Reservation.builder()
                .year(reservationDTO.getYear())
                .month(reservationDTO.getMonth())
                .day(reservationDTO.getDay())
                .start_time(reservationDTO.getStartTime())
                .end_time(reservationDTO.getEndTime())
                .duration(reservationDTO.getDuration())
                .build();

    }

}
