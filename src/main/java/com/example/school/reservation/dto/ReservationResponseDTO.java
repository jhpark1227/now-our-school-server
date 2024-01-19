package com.example.school.reservation.dto;

import com.example.school.domain.Facility;
import com.example.school.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReservationResponseDTO {
    //예약 내역
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailDTO{
        String year;
        String month;
        String day;
        String duration;
        String start_time;
        String end_time;
        Facility facility;
        Member member;

    }
    //예약 내역 리스트
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
        Boolean isLast;
    }

    //예약 가능한 시간
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class bookedUpDTO{
        String year;
        String month;
        String day;
        String startTime;
        String endTime;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class bookedUpListDTO{
        List<bookedUpDTO>  bookedUpList;
    }
}
