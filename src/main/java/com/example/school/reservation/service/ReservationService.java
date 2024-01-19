package com.example.school.reservation.service;

import com.example.school.reservation.converter.ReservationConverter;
import com.example.school.domain.Facility;
import com.example.school.domain.Member;
import com.example.school.domain.Reservation;
import com.example.school.facility.repository.FacilityRepository;
import com.example.school.facility.service.FacilityService;
import com.example.school.reservation.dto.ReservationRequestDTO;
import com.example.school.reservation.repository.ReservationRepository;
import com.example.school.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final FacilityRepository facilityRepository;
    private final UserService userService;
    private final FacilityService facilityService;

    //예약기능
    @Transactional
    public Reservation createReservation(ReservationRequestDTO.ReservationDTO reservationDTO){
        Reservation reservation = ReservationConverter.reservation(reservationDTO);
        Member member = userService.findById(reservationDTO.getMemberId());
        Facility facility = facilityService.findById(reservationDTO.getFacilityId());
        reservation.setMember(member);
        reservation.setFacility(facility);
        return reservationRepository.save(reservation);
    }
    //예약 불가능한 시간대
    public List<Reservation> possible_time(Long facilityId,String year,String month,String day){
         return reservationRepository.findAllByFacilityIdAndYearAndMonthAndDay(facilityId, year, month, day);

    }
    //예약 연장
    @Transactional
    public Reservation extendTime(Long reservationId, Integer extendTime) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation != null) {
            int newEndTime = reservation.getEnd_time() + extendTime;

            Long facilityId = reservation.getFacility().getId();
            String year = reservation.getYear();
            String month = reservation.getMonth();
            String day = reservation.getDay();
            List<Reservation> otherReservations = reservationRepository.findAllByFacilityIdAndYearAndMonthAndDay(facilityId, year, month, day)
                    .stream()
                    .filter(otherReservation -> !otherReservation.getId().equals(reservation.getId()))  // 현재 예약을 제외
                    .filter(otherReservation -> otherReservation.getStart_time() > reservation.getStart_time())  // 시작 시간이 newEndTime보다 큰 경우 필터링
                    .collect(Collectors.toList());
            // 다른 예약들의 startTime과 비교하여 연장이 가능한지 확인
            boolean isExtensionAllowed = otherReservations.stream()
                    .allMatch(otherReservation -> newEndTime <= otherReservation.getStart_time());

            if (isExtensionAllowed) {
                // 연장 가능한 경우, endTime 업데이트
                reservation.setEnd_time(newEndTime);
                return reservationRepository.save(reservation);
            } else {
                // 연장이 불가능한 경우에 대한 처리
                throw new RuntimeException("예약 연장이 불가능합니다. 다른 예약과 시간이 겹칩니다.");
            }
        } else {
            // 예약이 존재하지 않는 경우에 대한 처리
            throw new RuntimeException("예약이 존재하지 않습니다.");
        }
    }

    //예약반납
    //예약연장(1시간 단위)
    //예약내역
        public Page<Reservation> getReservation(Long memberId,Integer page){
            return reservationRepository.findAllByMemberId(memberId, PageRequest.of(page-1, 10));
        }
    //시설물 마다 예약현황

}
