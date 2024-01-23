package com.example.school.facility.dto;

import com.example.school.domain.Building;
import com.example.school.domain.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class FacilityResponseDTO {

    @Getter @AllArgsConstructor
    public static class Categories{
        List<CategoryWithFacilities> categories;
        int count;
    }

    @Getter @AllArgsConstructor
    public static class CategoryWithFacilities{
        String name;
        List<FacilityIdAndName> facilities;
        int count;

        public CategoryWithFacilities(Theme theme){
            name = theme.getName();
            facilities = theme.getFacilities().stream().map(fac->new FacilityIdAndName(fac.getId(),fac.getName()))
                    .collect(Collectors.toList());
            count = facilities.size();
        }
        public CategoryWithFacilities(Building theme){
            name = theme.getName();
            facilities = theme.getFacilities().stream().map(fac->new FacilityIdAndName(fac.getId(),fac.getName()))
                    .collect(Collectors.toList());
            count = facilities.size();
        }
    }

    @Getter @AllArgsConstructor
    public static class FacilityIdAndName{
        Long id;
        String name;
    }
}
