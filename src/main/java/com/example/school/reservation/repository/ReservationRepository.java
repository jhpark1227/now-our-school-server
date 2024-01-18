package com.example.school.reservation.repository;

import com.example.school.domain.Member;
import com.example.school.domain.Reservation;
import com.example.school.reservation.dto.ReservationRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Page<Reservation> findAllByMember(Long memberId, PageRequest PageRequest);
    List<Reservation> findAllByFacilityIdAndYearAndMonthAndDay(Long facilityId,String year,String month,String day);
}
