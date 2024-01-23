package com.example.school.faq.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.domain.FAQ;
import com.example.school.faq.dto.FAQRes;
import com.example.school.faq.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api/v1/faq")
@RequiredArgsConstructor
public class FAQController {
    private final FAQService FAQService;

    @GetMapping("/sample")
    public ApiResponse<FAQRes.FAQSamples> getFAQSamples(){
        List<FAQ> entities = FAQService.getFAQSamples();

        List<FAQRes.FAQSample> list =
                entities.stream().map(entity->new FAQRes.FAQSample(entity.getId(),entity.getTitle()))
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(new FAQRes.FAQSamples(list,list.size()));
    }
}
