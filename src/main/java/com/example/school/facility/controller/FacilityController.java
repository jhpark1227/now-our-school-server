package com.example.school.facility.controller;

import com.example.school.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;
}
