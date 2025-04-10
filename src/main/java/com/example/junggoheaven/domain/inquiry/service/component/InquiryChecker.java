package com.example.junggoheaven.domain.inquiry.service.component;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.inquiry.repository.InquiryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class InquiryChecker {

	private final InquiryRepository	inquiryRepository;

	public Boolean isInquiryByWriterId(Long inquiryId, Long userId) {
		return inquiryRepository.existsByIdAndWriterId(inquiryId, userId);
	}
}
