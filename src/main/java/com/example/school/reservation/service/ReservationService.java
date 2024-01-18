package com.example.school.reservation.service;

import com.example.school.domain.Facility;
import com.example.school.domain.Member;
import com.example.school.domain.Reservation;
import com.example.school.facility.repository.FacilityRepository;
import com.example.school.reservation.dto.ReservationRequestDTO;
import com.example.school.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final FacilityRepository facilityRepository;

    //예약기능
    @Transactional
    public void createReservation(Member member){
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservationRepository.save(reservation);
    }
    //예약 불가능한 시간대
    public List<Reservation> possible_time(Long facilityId,String year,String month,String day){
         return reservationRepository.findAllByFacilityIdAndYearAndMonthAndDay(facilityId, year, month, day);

    }
    //예약반납
    //예약연장(1시간 단위)
    //예약내역
    public Page<Reservation> getReservation(Long memberId,Integer page){
        return reservationRepository.findAllByMember(memberId, PageRequest.of(page, 10));
    }
    //예약현황
}
