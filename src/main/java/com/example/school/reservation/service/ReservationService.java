package com.example.school.reservation.service;

import com.example.school.domain.Facility;
import com.example.school.domain.Member;
import com.example.school.domain.Reservation;
import com.example.school.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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
    //예약 가능 시간대
    public void possible_time(Facility facility){
        // 예약 가능한 시간대를 저장할 리스트
        List<String> availableTimes = new ArrayList<>();

        // 오늘 날짜를 구합니다.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // 예약 가능한 시작 시간 (오전 9시)
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // 오전 9시부터 오후 8시까지 한 시간 단위로 반복
        while (calendar.get(Calendar.HOUR_OF_DAY) <= 20) {
            // 예약 가능한 시간을 리스트에 추가
            availableTimes.add(new SimpleDateFormat("HH:mm").format(calendar.getTime()));

            // 다음 시간으로 이동
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        }

        // 예약 가능한 시간을 출력 또는 필요한 처리를 수행
        for (String time : availableTimes) {
            System.out.println(time);
        }
    }
    //예약반납
    //예약연장(1시간 단위)
    //예약내역
    public Page<Reservation> getReservation(Long memberId,Integer page){
        return reservationRepository.findAllByMember(memberId, PageRequest.of(page, 10));
    }
    //예약현황
}
