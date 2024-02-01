package com.example.school.announcement.repository;

import com.example.school.entity.Announcement;
import com.example.school.entity.School;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, AnnouncementRepositoryCustom{
    List<Announcement> findBySchoolOrderByCreatedAtDesc(School school, PageRequest page);
}
