package com.example.school.facility.service;

import com.example.school.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
}
