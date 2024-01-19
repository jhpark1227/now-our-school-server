package com.example.school.reservation.dto;

import com.example.school.domain.Facility;
import lombok.Getter;

public class ReservationRequestDTO {
    //예약 내역 확인
    @Getter
    public static class DetailDTO{
        Long memberId;
        Integer page;

    }
    //예약반납
    @Getter
    public static class ReturnDTO{

    }
    //예약연장
    @Getter
    public static class ExtendDTO{

    }
    //예약현황
    @Getter
    public static class StatusDTO{

    }
    //예약기능
    @Getter
    public static class ReservationDTO{
        Long memberId;
        Long facilityId;
        String startTime;
        String endTime;
        String duration;
        String year;
        String month;
        String day;
    }
    //예약 불가능한 시간대
    @Getter
    public static class bookedUpDTO{
        Long facilityId;
        String year;
        String month;
        String day;
    }

}
