package com.example.school.reservation.service;

import com.example.school.domain.Image;
import com.example.school.domain.Reservation;
import com.example.school.reservation.converter.ImageConverter;
import com.example.school.reservation.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;
    @Transactional
    public void save(String imgUrl, Reservation reservation){
        Image image = ImageConverter.image(imgUrl);
        image.setReservation(reservation);
        imageRepository.save(image);
    }

}
