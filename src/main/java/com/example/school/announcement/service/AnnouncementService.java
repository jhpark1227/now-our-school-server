package com.example.school.announcement.service;

import com.example.school.announcement.repository.AnnouncementRepository;
import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.domain.Announcement;
import com.example.school.domain.Member;
import com.example.school.domain.School;
import com.example.school.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
