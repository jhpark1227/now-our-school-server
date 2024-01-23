package com.example.school.inquiry.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.domain.FAQ;
import com.example.school.inquiry.dto.InquiryRes;
import com.example.school.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api/v1/inquiry")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping("/faq/sample")
    public ApiResponse<InquiryRes.FAQSamples> getFAQSamples(){
        List<FAQ> entities = inquiryService.getFAQSamples();

        List<InquiryRes.FAQSample> list =
                entities.stream().map(entity->new InquiryRes.FAQSample(entity.getId(),entity.getTitle()))
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(new InquiryRes.FAQSamples(list,list.size()));
    }
}
