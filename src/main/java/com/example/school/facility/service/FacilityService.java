package com.example.school.facility.service;

import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.Building;
import com.example.school.domain.Facility;
import com.example.school.domain.Member;
import com.example.school.domain.Theme;
import com.example.school.facility.repository.BuildingRepository;
import com.example.school.facility.repository.ThemeRepository;
import com.example.school.facility.repository.FacilityRepository;
import com.example.school.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final ThemeRepository themeRepository;
    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;
    public Facility findById(Long id){
        return facilityRepository.findById(id).get();
    }

    public List<Theme> getListByTheme(String email) {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        List<Theme> entities = themeRepository.findBySchoolWithFacility(member.getSchool());

        return entities;
    }

    public List<Building> getListByBuilding(String email) {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        List<Building> entities = buildingRepository.findBySchoolWithFacility(member.getSchool());

        return entities;
    }

    public List<Building> getMarkers(String email) {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        return buildingRepository.findAllBySchool(member.getSchool());
    }
}
