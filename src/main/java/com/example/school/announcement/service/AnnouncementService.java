package com.example.school.announcement.service;

import com.example.school.announcement.dto.AnnouncementRes;
import com.example.school.announcement.repository.AnnouncementRepository;
import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.Announcement;
import com.example.school.domain.Member;
import com.example.school.domain.School;
import com.example.school.domain.enums.AnnouncementType;
import com.example.school.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service @Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    public List<Announcement> getSamples(Long memberId) {
        Member member = userRepository.findById(memberId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        School school = member.getSchool();

        List<Announcement> entities = announcementRepository.findBySchoolOrderByCreatedAtDesc(
                school,
                PageRequest.of(0,4)
        );

        return entities;
    }

    public AnnouncementRes.Detail getDetail(Long id) {
        Announcement entity = announcementRepository.findById(id)
                .orElseThrow(()->new GeneralException(ErrorStatus.ANNOUNCE_NOT_FOUND));

        return new AnnouncementRes.Detail(entity);
    }

    public AnnouncementRes.ListDto getList(Long id, String type, Integer page) {
        Member member = userRepository.findById(id)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        AnnouncementType announcementType = null;
        if(StringUtils.hasText(type)){
            announcementType = AnnouncementType.valueOf(type.toUpperCase());
        }

        Page<Announcement> entities = announcementRepository.findByType(member.getSchool(), announcementType, PageRequest.of(page-1,15));

        return new AnnouncementRes.ListDto(entities);
    }
}
