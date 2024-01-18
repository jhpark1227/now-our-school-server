package com.example.school.reservation.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.converter.ReservationConverter;
import com.example.school.domain.Reservation;
import com.example.school.reservation.dto.ReservationRequestDTO;
import com.example.school.reservation.dto.ReservationResponseDTO;
import com.example.school.reservation.repository.ReservationRepository;
import com.example.school.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    @PostMapping("/details")
    public ApiResponse<ReservationResponseDTO.DetailResultDTO> getReservation(@RequestBody ReservationRequestDTO.DetailDTO detailDTO) {
        Long memberId = detailDTO.getMemberId();
        Integer page = detailDTO.getPage();
        System.out.println("page = "+page);
        Page<Reservation> reservationList = reservationService.getReservation(memberId,page);
        return ApiResponse.onSuccess(ReservationConverter.detailResultListDTO(reservationList));
    }


}
