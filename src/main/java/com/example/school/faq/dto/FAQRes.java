package com.example.school.faq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class FAQRes {

    @Getter @AllArgsConstructor
    public static class FAQSamples{
        List<FAQSample> list;
        int count;
    }

    @Getter @AllArgsConstructor
    public static class FAQSample{
        Long id;
        String title;
    }
}
