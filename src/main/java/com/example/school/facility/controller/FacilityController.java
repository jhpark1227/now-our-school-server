package com.example.school.facility.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.domain.Building;
import com.example.school.domain.Facility;
import com.example.school.domain.Theme;
import com.example.school.domain.enums.FacilityTag;
import com.example.school.facility.dto.FacilityResponseDTO;
import com.example.school.facility.service.FacilityQueryService;
import com.example.school.facility.service.FacilityService;
import com.example.school.validation.annotation.CheckPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController @RequestMapping("api/v1/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;
    private final FacilityQueryService facilityQueryService;

    @GetMapping("/category/theme")
    public ApiResponse<FacilityResponseDTO.Categories> getListByTheme(@RequestParam("email") String email){
        List<Theme> entities = facilityService.getListByTheme(email);

        List<FacilityResponseDTO.CategoryWithFacilities> list =
                entities.stream().map(FacilityResponseDTO.CategoryWithFacilities::new)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(new FacilityResponseDTO.Categories(list,list.size()));
    }

    @GetMapping("/category/building")
    public ApiResponse<FacilityResponseDTO.Categories> getListByBuilding(@RequestParam("email") String email){
        List<Building> entities = facilityService.getListByBuilding(email);

        List<FacilityResponseDTO.CategoryWithFacilities> list =
                entities.stream().map(FacilityResponseDTO.CategoryWithFacilities::new)
                        .collect(Collectors.toList());

        return ApiResponse.onSuccess(new FacilityResponseDTO.Categories(list,list.size()));
    }

    @GetMapping("/map")
    public ApiResponse<FacilityResponseDTO.Markers> getMarkers(@RequestParam("email") String email){
        List<Building> entities = facilityService.getMarkers(email);

        List<FacilityResponseDTO.Marker> list = entities.stream()
                .map(entity->new FacilityResponseDTO.Marker(entity.getId(),entity.getLatitude(),entity.getLongitude()))
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(new FacilityResponseDTO.Markers(list,list.size()));
    }

    @GetMapping("/suggestion")
    public ApiResponse<FacilityResponseDTO.Tags> getSuggestion(@RequestParam("userId")String userId){
        List<Facility> entities = facilityService.getSuggestion(userId);

        Map<FacilityTag,List<Facility>> map = entities.stream().collect(Collectors.groupingBy(Facility::getTag));
        List<FacilityResponseDTO.Tag> res = map.keySet().stream().map(key->{
            List<FacilityResponseDTO.FacilityWithTag> list =
                    map.get(key).stream().map(value->{
                        return new FacilityResponseDTO.FacilityWithTag(value.getId(),value.getName(),value.getImageURL());
                    }).collect(Collectors.toList());
            return new FacilityResponseDTO.Tag(key.getTag(),list,list.size());
        }).collect(Collectors.toList());

        return ApiResponse.onSuccess(new FacilityResponseDTO.Tags(res));
    }

    @GetMapping("/{facilityId}")
    public ApiResponse<FacilityResponseDTO.Detail> getDetail(@PathVariable("facilityId")Long facilityId){
        FacilityResponseDTO.Detail res = facilityQueryService.getDetail(facilityId);

        return ApiResponse.onSuccess(res);
    }

    @GetMapping("/{facilityId}/img")
    public ApiResponse<FacilityResponseDTO.Images> getImages(
            @PathVariable("facilityId")Long facilityId,
            @RequestParam("page") @CheckPage Integer page
    ){
        FacilityResponseDTO.Images res = facilityQueryService.getImages(facilityId, page);

        return ApiResponse.onSuccess(res);
    }
}
