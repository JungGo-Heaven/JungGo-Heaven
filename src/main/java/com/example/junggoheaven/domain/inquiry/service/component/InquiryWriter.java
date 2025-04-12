package com.example.junggoheaven.domain.inquiry.service.component;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.inquiry.repository.InquiryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryWriter {

	private final InquiryRepository inquiryRepository;

	public Inquiry saveInquiry(Inquiry inquiry) {
		return inquiryRepository.save(inquiry);
	}
}
