package com.example.school.reservation.repository;

import com.example.school.domain.Image;
import com.example.school.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
