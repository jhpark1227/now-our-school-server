package com.example.school.announcement.controller;

import com.example.school.announcement.dto.AnnouncementRes;
import com.example.school.announcement.service.AnnouncementService;
import com.example.school.apiPayload.ApiResponse;
import com.example.school.domain.Announcement;
import com.example.school.domain.Member;
import com.example.school.validation.annotation.CheckAnnouncementType;
import com.example.school.validation.annotation.CheckPage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api/v1/announcement")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping("/sample")
    public ApiResponse<AnnouncementRes.Samples> getSamples(@RequestParam("memberId") Long memberId){
        List<Announcement> entities = announcementService.getSamples(memberId);

        List<AnnouncementRes.Sample> list =
                entities.stream().map(entity->new AnnouncementRes.Sample(entity.getId(), entity.getTitle()))
                        .collect(Collectors.toList());

        return ApiResponse.onSuccess(new AnnouncementRes.Samples(list,list.size()));
    }

    @GetMapping("/list")
    public ApiResponse<AnnouncementRes.ListDto> getList(
            @RequestParam(name = "page") @CheckPage Integer page,
            @RequestParam(name = "type",required = false) @CheckAnnouncementType String type,
            Authentication auth
    ){
        Member member = (Member)auth.getPrincipal();

        AnnouncementRes.ListDto res = announcementService.getList(member.getId(),type, page);

        return ApiResponse.onSuccess(res);
    }

    @GetMapping("/{id}")
    public ApiResponse<AnnouncementRes.Detail> getDetail(@PathVariable("id") Long id){
        AnnouncementRes.Detail res = announcementService.getDetail(id);

        return ApiResponse.onSuccess(res);
    }
}
