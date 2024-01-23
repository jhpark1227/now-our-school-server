package com.example.school.user.controller;

import com.example.school.apiPayload.ApiResponse;
import com.example.school.domain.Inquiry;
import com.example.school.domain.Reservation;
import com.example.school.domain.Review;
import com.example.school.reservation.converter.ReservationConverter;
import com.example.school.reservation.dto.ReservationResponseDTO;
import com.example.school.user.converter.UserConverter;
import com.example.school.user.dto.UserRequestDTO;
import com.example.school.user.dto.UserResponseDTO;
import com.example.school.user.service.UserCommandService;
import com.example.school.user.service.UserQueryService;
import com.example.school.validation.annotation.ExistFacility;
import com.example.school.validation.annotation.ExistMember;

import com.example.school.validation.annotation.ExistReview;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    //리뷰 작성
    @PostMapping("/review")
    @Operation(summary = "리뷰 작성 API",description = "리뷰를 작성하는 API")
    public ApiResponse<UserResponseDTO.CreateReviewResultDTO> createReview(@RequestBody @Valid UserRequestDTO.ReviewDTO request,
                                                                           @ExistFacility @RequestParam(name = "facilityId") Long facilityId,
                                                                           @ExistMember @RequestParam(name = "memberId") Long memberId){
        Review review = userCommandService.createReview(memberId, facilityId, request);
        return ApiResponse.onSuccess(UserConverter.toCreateReviewResultDTO(review));
    }
    //시설별 리뷰 조회
<<<<<<< HEAD
    @GetMapping("/details/{facilityId}/review")
    public ApiResponse<UserResponseDTO.ReviewPreViewListDTO> facilityReview( @PathVariable(name="facilityId") Long facilityId,
=======
    @GetMapping("/{facilityId}/reviews/byFacility")
    @Operation(summary = "시설별 리뷰 조회 API",description = "시설별로 리뷰목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    public ApiResponse<UserResponseDTO.ReviewPreViewListDTO> facilityReview(@PathVariable(name="facilityId") Long facilityId,
>>>>>>> 8797a556b92930b06a805951dcae3a7723f21ec4
                                                                            @RequestParam(name="page") Integer page){
        Page<Review> reviewList = userQueryService.findByFacility(facilityId, page);
        return ApiResponse.onSuccess(UserConverter.reviewPreViewListDTO(reviewList));
    }
    //나의 리뷰 조회
    @GetMapping("/{memberId}/reviews/byMember")
    @Operation(summary = "나의 리뷰 목록 조회 API",description = "나의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<UserResponseDTO.ReviewPreViewListDTO> getReviewList(@ExistMember @PathVariable(name = "memberId") Long memberId, @RequestParam(name = "page") Integer page){
        Page<Review> reviewList = userQueryService.getReviewList(memberId, page);
        UserResponseDTO.ReviewPreViewListDTO reviewPreViewListDTO = UserConverter.reviewPreViewListDTO(reviewList);
        return ApiResponse.onSuccess(reviewPreViewListDTO);
    }
    //리뷰수정
    @PutMapping("/review/modify")
    @Operation(summary = "나의 리뷰 수정 API",description = "나의 리뷰를 수정하는 API이며, reviewId, facilityId, memberId가 모두 일치할 시 수정가능합니다")
    public ApiResponse<UserResponseDTO.UpdateReviewResultDTO> modifyReview(
            @ExistReview @RequestParam(name = "reviewId") Long reviewId,
            @RequestBody @Valid UserRequestDTO.ReviewDTO request,
            @ExistFacility @RequestParam(name = "facilityId") Long facilityId,
            @ExistMember @RequestParam(name = "memberId") Long memberId) {

        Review updatedReview = userCommandService.updateReview(memberId, facilityId, reviewId, request);
        return ApiResponse.onSuccess(UserConverter.toUpdateReviewResultDTO(updatedReview));
    }
    //리뷰삭제
    @DeleteMapping("/review/delete")
    @Operation(summary = "나의 리뷰 삭제 API", description = "나의 리뷰를 삭제하는 API이며, reviewId, facilityId, memberId가 모두 일치할 시 삭제 가능합니다")
    public ApiResponse<String> deleteReview(
            @ExistReview @RequestParam(name = "reviewId") Long reviewId,
            @ExistFacility @RequestParam(name = "facilityId") Long facilityId,
            @ExistMember @RequestParam(name = "memberId") Long memberId) {

        userCommandService.deleteReview(memberId, facilityId, reviewId);
        return ApiResponse.onSuccess("Review deleted successfully");
    }
    //문의하기
    @PostMapping("/inquiry")
    @Operation(summary = "문의하기 API",description = "문의하는 API")
    public ApiResponse<UserResponseDTO.CreateInquiryResultDTO> createInquiry(@RequestBody @Valid UserRequestDTO.InquiryDTO request,
                                                                           @ExistMember @RequestParam(name = "memberId") Long memberId){
        Inquiry inquiry = userCommandService.createInquiry(memberId, request);
        return ApiResponse.onSuccess(UserConverter.toCreateInquiryResultDTO(inquiry));
    }
}
