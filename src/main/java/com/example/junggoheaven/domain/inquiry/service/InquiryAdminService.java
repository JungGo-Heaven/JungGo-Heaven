package com.example.junggoheaven.domain.inquiry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.inquiry.dto.request.InquiryStatusRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.request.RespondInquiryRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.response.AdminInquiryListResponseDto;
import com.example.junggoheaven.domain.inquiry.dto.response.AdminInquiryResponseDto;
import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.inquiry.eunms.InquiryStatus;
import com.example.junggoheaven.domain.inquiry.exception.AlreadyCompletedInquiryException;
import com.example.junggoheaven.domain.inquiry.exception.AlreadyDeletedInquiryException;
import com.example.junggoheaven.domain.inquiry.exception.InquiryStatusSameException;
import com.example.junggoheaven.domain.inquiry.exception.InvalidRespondentException;
import com.example.junggoheaven.domain.inquiry.exception.RespondNotFoundException;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryAdminService {

	private final UserFinder userFinder;
	private final InquiryFinder inquiryFinder;

	@Transactional
	public AdminInquiryResponseDto respondToInquiry(Long inquiryId, Long userId, RespondInquiryRequestDto requestDto) {
		Inquiry inquiry = inquiryFinder.findInquiryById(inquiryId);
		isDeleteChecked(inquiry);
		if (inquiry.getStatus().equals(InquiryStatus.COMPLETED)) {
			throw new AlreadyCompletedInquiryException();
		}

		User responder = userFinder.findByUserId(userId);
		String response = requestDto.getResponse();
		inquiry.respond(responder, response);

		return AdminInquiryResponseDto.from(inquiry);
	}

	@Transactional
	public AdminInquiryResponseDto updateRespond(Long inquiryId, Long userId, RespondInquiryRequestDto requestDto) {
		Inquiry inquiry = inquiryFinder.findInquiryById(inquiryId);
		isDeleteChecked(inquiry);
		if (inquiry.getStatus().equals(InquiryStatus.WAITING)) {
			throw new RespondNotFoundException();
		}
		if (!inquiry.getRespondent().getId().equals(userId)) {
			throw new InvalidRespondentException();
		}

		String updateResponse = requestDto.getResponse();
		inquiry.updateResponse(updateResponse);

		return AdminInquiryResponseDto.from(inquiry);
	}

	@Transactional
	public AdminInquiryResponseDto changeInquiryStatus(InquiryStatusRequestDto requestDto) {
		Long inquiryId = requestDto.getInquiryId();
		InquiryStatus updateStatus = requestDto.getStatus();
		Inquiry inquiry = inquiryFinder.findInquiryById(inquiryId);

		if (inquiry.getStatus().equals(updateStatus)) {
			throw new InquiryStatusSameException();
		}

		inquiry.updateStatus(updateStatus);
		return AdminInquiryResponseDto.from(inquiry);
	}

	public Page<AdminInquiryListResponseDto> getInquiryList(String title, String status, String writerEmail,
		int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Inquiry> inquiries = inquiryFinder.findAllByForAdmin(title, status, writerEmail, pageable);

		return inquiries.map(AdminInquiryListResponseDto::from);
	}

	private void isDeleteChecked(Inquiry inquiry) {
		if (inquiry.getStatus().equals(InquiryStatus.DELETED)) {
			throw new AlreadyDeletedInquiryException();
		}
	}
}
