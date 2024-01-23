package com.example.school.inquiry.service;

import com.example.school.domain.FAQ;
import com.example.school.inquiry.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryService {
    private final FAQRepository faqRepository;

    public List<FAQ> getFAQSamples() {
        return faqRepository.findTop4By();
    }
}
