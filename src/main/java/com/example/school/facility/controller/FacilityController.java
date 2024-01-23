package com.example.school.facility.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.domain.Theme;
import com.example.school.facility.dto.FacilityResponseDTO;
import com.example.school.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequestMapping("api/v1/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping("/category/theme")
    public ApiResponse<FacilityResponseDTO.Categories> getListByTheme(@RequestParam("email") String email){
        List<Theme> entities = facilityService.getListByTheme(email);

        List<FacilityResponseDTO.CategoryWithFacilities> list =
                entities.stream().map(FacilityResponseDTO.CategoryWithFacilities::new)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(new FacilityResponseDTO.Categories(list,list.size()));
    }
}
