package com.example.school.reservation.dto;

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

    }

}
