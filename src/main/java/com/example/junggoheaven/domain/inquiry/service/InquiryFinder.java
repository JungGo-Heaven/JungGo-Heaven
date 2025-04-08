package com.example.junggoheaven.domain.inquiry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.inquiry.exception.InquiryNotFoundException;
import com.example.junggoheaven.domain.inquiry.exception.InvalidInquiryException;
import com.example.junggoheaven.domain.inquiry.repository.InquiryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryFinder {

	private final InquiryRepository inquiryRepository;

	public Inquiry findInquiryById(Long inquiryId) {
		return inquiryRepository.findById(inquiryId).orElseThrow(InquiryNotFoundException::new);
	}

	public Inquiry findByIdForUser(Long inquiryId) {
		return inquiryRepository.findByIdAndStatusIsNotDeleted(inquiryId);
	}

	public Inquiry findByValidWriter(Long inquiryId, Long writerId) {
		Inquiry inquiry = findInquiryById(inquiryId);
		if (!inquiry.getWriter().getId().equals(writerId)) {
			throw new InvalidInquiryException();
		}
		return inquiry;
	}

	public Page<Inquiry> findAllByWriterForUser(Long writerId, Pageable pageable) {
		return inquiryRepository.findAllByWriterIdAndStatusIsNotDeleted(writerId, pageable);
	}

	public Page<Inquiry> findAllByWriter(Long writerId, Pageable pageable) {
		return inquiryRepository.findAllByWriterId(writerId, pageable);
	}

	public Page<Inquiry> findAllByForAdmin(String title, String status, String writerEmail, Pageable pageable) {
		return inquiryRepository.findAllByForAdmin(title, status, writerEmail, pageable);
	}
}
