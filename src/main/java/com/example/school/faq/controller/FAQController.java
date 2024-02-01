package com.example.school.faq.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.entity.FAQ;
import com.example.school.faq.dto.FAQRes;
import com.example.school.faq.service.FAQService;
import com.example.school.validation.annotation.CheckFaqType;
import com.example.school.validation.annotation.CheckPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController @RequestMapping("/api/v1/faq")
@RequiredArgsConstructor
public class FAQController {
    private final FAQService FAQService;

    @GetMapping("/sample")
    public ApiResponse<FAQRes.FAQSamples> getFAQSamples(){
        FAQRes.FAQSamples res = FAQService.getFAQSamples();

        return ApiResponse.onSuccess(res);
    }

    @GetMapping("list")
    public ApiResponse<FAQRes.FAQList> getList(
            @RequestParam("type") @CheckFaqType String type,
            @RequestParam("page") @CheckPage Integer page){
        FAQRes.FAQList res = FAQService.getList(type, page);

        return ApiResponse.onSuccess(res);
    }
}
