package com.example.school.announcement.dto;

import com.example.school.domain.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class AnnouncementRes {

    @Getter @AllArgsConstructor
    public static class Samples{
        List<Sample> list;
        int count;
    }

    @Getter @AllArgsConstructor
    public static class Sample{
        Long id;
        String title;
    }

    @Getter @AllArgsConstructor
    public static class Detail {
        Long id;
        LocalDate date;
        String title;
        String content;

        public Detail(Announcement entity){
            id = entity.getId();
            date = entity.getCreatedAt().toLocalDate();
            title = entity.getTitle();
            content = entity.getContent();
        }
    }
}
