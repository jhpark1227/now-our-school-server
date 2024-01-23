package com.example.school.announcement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
