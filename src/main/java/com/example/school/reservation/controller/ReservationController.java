package com.example.school.reservation.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.reservation.converter.ReservationConverter;
import com.example.school.domain.Reservation;
import com.example.school.reservation.dto.ReservationRequestDTO;
import com.example.school.reservation.dto.ReservationResponseDTO;
import com.example.school.reservation.service.ReservationService;
import com.example.school.validation.annotation.ExistFacility;
import com.example.school.validation.annotation.ExistMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservation")
public class ReservationController {
    private final ReservationService reservationService;

    //예약하기
    @PostMapping("")
    public ApiResponse<Reservation> reserve(@RequestBody ReservationRequestDTO.ReservationDTO reservationDTO){
        //facilityId로 facility 찾는 코드 작성
        return ApiResponse.onSuccess(reservationService.createReservation(reservationDTO));

    }

    //예약 내역 확인
    @GetMapping("/details")
    public ApiResponse<ReservationResponseDTO.DetailResultDTO> getReservation(@RequestParam(name="memberId") Long memberId, @RequestParam(name="page")Integer page) {
        Page<Reservation> reservationList = reservationService.getReservation(memberId,page);
        return ApiResponse.onSuccess(ReservationConverter.detailResultListDTO(reservationList));
    }

    //예약 불가능한 시간대
    @GetMapping("/time")
    public ApiResponse<ReservationResponseDTO.bookedUpListDTO> checkTime(@RequestParam(name = "facilityId")Long facilityId,
            @RequestParam(name="year")String year,@RequestParam(name = "month")String month,@RequestParam(name = "day") String day) {

        List<Reservation> reservations = reservationService.possible_time(facilityId, year, month, day);
        return ApiResponse.onSuccess(ReservationConverter.bookedUpListDTO(reservations));
    }
    //예약 연장하기
    @PostMapping("/extend")
    public ApiResponse<ReservationResponseDTO.DetailDTO> extendTime(@RequestBody ReservationRequestDTO.ExtendDTO extendDTO) {
        try {
            Reservation reservation = reservationService.extendTime(extendDTO.getReservation_id(), extendDTO.getExtendTime());
            return ApiResponse.onSuccess(ReservationConverter.detailResultDTO(reservation));
        } catch (RuntimeException e) {
            // RuntimeException이 발생한 경우에 대한 처리
            return ApiResponse.onFailure("COMMON400", e.getMessage()); // 적절한 에러 코드 및 메시지 설정
        }
    }


}
