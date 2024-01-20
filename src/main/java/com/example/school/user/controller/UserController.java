package com.example.school.user.controller;

import com.example.school.apiPayload.ApiResponse;
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
    //나의 리뷰 조회
    @GetMapping("/{memberId}/reviews")
    @Operation(summary = "나의 리뷰 목록 조회 API",description = "나의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "나의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다."),

    })
    public ApiResponse<UserResponseDTO.ReviewPreViewListDTO> getReviewList(@ExistMember @PathVariable(name = "memberId") Long memberId, @RequestParam(name = "page") Integer page){
        Page<Review> reviewList = userQueryService.getReviewList(memberId, page);
        UserResponseDTO.ReviewPreViewListDTO reviewPreViewListDTO = UserConverter.reviewPreViewListDTO(reviewList);
        return ApiResponse.onSuccess(reviewPreViewListDTO);
    }

}
