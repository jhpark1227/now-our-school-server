package com.example.school.faq.service;

import com.example.school.entity.FAQ;
import com.example.school.entity.enums.FaqType;
import com.example.school.faq.dto.FAQRes;
import com.example.school.faq.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public FAQRes.FAQList getList(String type, Integer page) {
        FaqType faqType = FaqType.valueOf(type.toUpperCase());
        Pageable pageRequest = PageRequest.of(page-1,15);

        Page<FAQ> entities = faqRepository.findByType(faqType, pageRequest);

        return new FAQRes.FAQList(entities);
    }
}
