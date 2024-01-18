package com.example.school.reservation.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.converter.ReservationConverter;
import com.example.school.domain.Reservation;
import com.example.school.reservation.dto.ReservationRequestDTO;
import com.example.school.reservation.dto.ReservationResponseDTO;
import com.example.school.reservation.repository.ReservationRepository;
import com.example.school.reservation.service.ReservationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservation")
public class ReservationController {
    private final ReservationService reservationService;
    //예약 내역 확인 1번
    @GetMapping("/details")
    public ApiResponse<ReservationResponseDTO.DetailResultDTO> getReservation(@RequestParam(name="memberId")Long memberId,@RequestParam(name="page")Integer page) {
        System.out.println("page = "+page);
        Page<Reservation> reservationList = reservationService.getReservation(memberId,page);
        return ApiResponse.onSuccess(ReservationConverter.detailResultListDTO(reservationList));
    }

    //예약 불가능한 시간대
    @GetMapping
    public ApiResponse<ReservationResponseDTO.bookedUpListDTO> checkTime(@RequestParam(name = "facilityId")Long facilityId,
            @RequestParam(name="year")String year,@RequestParam(name = "month")String month,@RequestParam(name = "day") String day) {

        List<Reservation> reservations = reservationService.possible_time(facilityId, year, month, day);
        return ApiResponse.onSuccess(ReservationConverter.bookedUpListDTO(reservations));

    }



}
