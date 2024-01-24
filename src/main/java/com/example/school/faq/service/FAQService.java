package com.example.school.faq.service;

import com.example.school.domain.FAQ;
import com.example.school.faq.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional(readOnly = true)
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository faqRepository;

    public List<FAQ> getFAQSamples() {
        return faqRepository.findTop4By();
    }
}
