package com.example.junggoheaven.domain.inquiry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.inquiry.dto.request.CreateInquiryRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.request.UpdateInquiryRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.response.UserInquiryListResponseDto;
import com.example.junggoheaven.domain.inquiry.dto.response.UserInquiryResponseDto;
import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.inquiry.enums.InquiryStatus;
import com.example.junggoheaven.domain.inquiry.exception.InvalidInquiryException;
import com.example.junggoheaven.domain.inquiry.service.component.InquiryFinder;
import com.example.junggoheaven.domain.inquiry.service.component.InquiryWriter;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryService {

	private final UserFinder userFinder;
	private final InquiryWriter inquiryWriter;
	private final InquiryFinder inquiryFinder;

	public UserInquiryResponseDto createInquiry(Long userId, CreateInquiryRequestDto requestDto) {
		User writer = userFinder.findByUserId(userId);
		String title = requestDto.getTitle();
		String body = requestDto.getBody();

		Inquiry save = inquiryWriter.saveInquiry(Inquiry.of(writer, title, body));

		return UserInquiryResponseDto.from(save);
	}

	public Page<UserInquiryListResponseDto> getMyInquiries(Long userId, int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return inquiryFinder.findAllByWriterForUser(userId, pageable).map(UserInquiryListResponseDto::from);
	}

	public UserInquiryResponseDto getInquiry(AuthUser authUser, Long inquiryId) {
		UserRole userRole = authUser.getRole();
		Long writerId = authUser.getId();
		Inquiry inquiry = inquiryFinder.findByIdForUser(inquiryId);

		if (!writerId.equals(inquiry.getWriter().getId()) && !userRole.equals(UserRole.ROLE_ADMIN)) {
			throw new InvalidInquiryException();
		}

		return UserInquiryResponseDto.from(inquiry);
	}

	@Transactional
	public UserInquiryResponseDto updateInquiry(Long inquiryId, Long userId, UpdateInquiryRequestDto requestDto) {
		Inquiry inquiry = inquiryFinder.findByValidWriter(inquiryId, userId);
		String title = requestDto.getTitle();
		String body = requestDto.getBody();

		if (title != null) {
			inquiry.updateTitle(title);
		}
		if (body != null) {
			inquiry.updateBody(body);
		}

		return UserInquiryResponseDto.from(inquiry);
	}

	public void deleteInquiry(Long inquiryId, Long userId) {
		Inquiry inquiry = inquiryFinder.findByValidWriter(inquiryId, userId);
		inquiryWriter.deleteInquiry(inquiry);
	}
}
