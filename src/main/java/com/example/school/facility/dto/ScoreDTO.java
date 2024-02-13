package com.example.school.facility.dto;

import com.example.school.domain.Facility;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ScoreDTO {
    private Facility facility;
    private Double newScore;
}
