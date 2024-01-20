package com.example.school.user.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.domain.Review;
import com.example.school.user.converter.UserConverter;
import com.example.school.user.dto.UserRequestDTO;
import com.example.school.user.dto.UserResponseDTO;
import com.example.school.user.service.UserCommandService;
import com.example.school.user.service.UserQueryService;
import com.example.school.validation.annotation.ExistFacility;
import com.example.school.validation.annotation.ExistMember;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PostMapping("/{facilityId}/review")
    public ApiResponse<UserResponseDTO.CreateReviewResultDTO> createReview(@RequestBody @Valid UserRequestDTO.ReviewDTO request,
                                                                           @ExistFacility @PathVariable(name = "facilityId") Long facilityId,
                                                                           @ExistMember @RequestParam(name = "memberId") Long memberId){
        Review review = userCommandService.createReview(memberId, facilityId, request);
        return ApiResponse.onSuccess(UserConverter.toCreateReviewResultDTO(review));
    }
    //시설별 리뷰 조회
    @GetMapping("/details/{facilityId}/review")
    public ApiResponse<UserResponseDTO.ReviewPreViewListDTO> facilityReview(@PathVariable(name="facilityId") Long facilityId,
                                                                            @RequestParam(name="page") Integer page){
        Page<Review> reviewList = userQueryService.findByFacility(facilityId, page);
        return ApiResponse.onSuccess(UserConverter.reviewPreViewListDTO(reviewList));
    }

}
