package com.example.school.faq.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.domain.FAQ;
import com.example.school.faq.dto.FAQRes;
import com.example.school.faq.service.FAQService;
import com.example.school.validation.annotation.CheckFaqType;
import com.example.school.validation.annotation.CheckPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("list")
    public ApiResponse<FAQRes.FAQList> getList(
            @RequestParam("type") @CheckFaqType String type,
            @RequestParam("page") @CheckPage Integer page){
        FAQRes.FAQList res = FAQService.getList(type, page);

        return ApiResponse.onSuccess(res);
    }
}
