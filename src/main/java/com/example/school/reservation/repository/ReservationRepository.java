package com.example.school.reservation.repository;

import com.example.school.domain.Member;
import com.example.school.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Page<Reservation> findAllByMember(Long memberId, PageRequest PageRequest);
}
