package com.example.school.reservation.service;

import com.example.school.domain.Member;
import com.example.school.domain.Reservation;
import com.example.school.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;

    //예약기능
    @Transactional
    public void createReservation(Member member){
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservationRepository.save(reservation);
    }
    //예약반납
    //예약연장
    //예약내역
    public Page<Reservation> getReservation(Long memberId,Integer page){
        return reservationRepository.findAllByMember(memberId, PageRequest.of(page, 10));
    }
    //예약현황
}
