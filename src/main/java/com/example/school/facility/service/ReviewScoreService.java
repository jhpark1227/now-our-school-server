package com.example.school.facility.service;

import com.example.school.facility.dto.ScoreDTO;
import com.example.school.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional
@RequiredArgsConstructor
public class ReviewScoreService {
    private final FacilityRepository facilityRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void updateScore(){
        List<ScoreDTO> list = facilityRepository.findAllWithReview();
        list.forEach(dto->{
            dto.getFacility().updateScore(dto.getNewScore());
        });
    }
}
