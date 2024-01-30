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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service @Transactional
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final ThemeRepository themeRepository;
    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;
    private final RedisTemplate redisTemplate;
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

    public List<Facility> getSuggestion(String userId) {
        Member member = userRepository.findByUserId(userId)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        return facilityRepository.findByBuildingSchoolAndTagIsNotNull(member.getSchool());
    }

    public void saveSearchLog(Long memberId, Long schoolId, String value) {
        String key = searchLogKeyBySchool(schoolId);
        redisTemplate.opsForList().rightPush(key,value);

        key = searchLogKey(memberId);
        Long size = redisTemplate.opsForZSet().size(key);
        if(size==10){
            redisTemplate.opsForZSet().removeRange(key,10,10);
        }
        redisTemplate.opsForZSet().add(key, value, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    }

    public String searchLogKey(Long memberId){
        return "SearchLog:"+memberId;
    }

    public String searchLogKeyBySchool(Long schoolId){
        return "School:"+schoolId;
    }
}
