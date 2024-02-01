package com.example.school.facility.service;

import com.example.school.apiPayload.GeneralException;
import com.example.school.apiPayload.status.ErrorStatus;
import com.example.school.entity.Member;
import com.example.school.facility.dto.FacilityResponseDTO;
import com.example.school.facility.dto.RestTemplateRes;
import com.example.school.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class LibraryService {

    private final UserRepository userRepository;

    @Value("${flask-server}")
    private String address;
    public FacilityResponseDTO.LibraryStatus getLibraryStatus(Long memberId){
        Member member = userRepository.findById(memberId)
                .orElseThrow(()->new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        URI uri = UriComponentsBuilder
                .fromUriString(address)
                .path("/api/v1/library")
                .queryParam("university",member.getSchool().getName())
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<RestTemplateRes.Common<RestTemplateRes.LibraryStatus>> res =
                restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        if(!res.getBody().getIsSuccess()){
            throw new GeneralException(ErrorStatus.NO_CONTENT);
        }

        RestTemplateRes.LibraryStatus status = res.getBody().getResult();

        List<FacilityResponseDTO.LibraryDetail> list = status.getList().stream().map(detail->{
            return FacilityResponseDTO.LibraryDetail.builder()
                    .name(detail.getName())
                    .total(detail.getTotal())
                    .current(detail.getCurrent())
                    .status(detail.getStatus()).build();
        }).collect(Collectors.toList());

        return new FacilityResponseDTO.LibraryStatus(list);
    }
}
