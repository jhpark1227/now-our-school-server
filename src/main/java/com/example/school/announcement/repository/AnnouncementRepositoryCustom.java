package com.example.school.announcement.repository;

import com.example.school.domain.Announcement;
import com.example.school.domain.School;
import com.example.school.domain.enums.AnnouncementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AnnouncementRepositoryCustom {
    Page<Announcement> findByType(School school, AnnouncementType type, Pageable pageable);
}
