package com.example.junggoheaven.domain.inquiry.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.inquiry.dto.request.InquiryStatusRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.request.RespondInquiryRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.response.AdminInquiryListResponseDto;
import com.example.junggoheaven.domain.inquiry.dto.response.AdminInquiryResponseDto;
import com.example.junggoheaven.domain.inquiry.service.InquiryAdminService;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Secured(UserRole.Authority.ADMIN)
public class InquiryAdminController {

	private final InquiryAdminService inquiryAdminService;

	@PatchMapping("/inquiries/{inquiryId}/response")
	public ResponseDto<AdminInquiryResponseDto> respondToInquiry(@PathVariable Long inquiryId,
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody RespondInquiryRequestDto requestDto) {
		return ResponseDto.success(inquiryAdminService.respondToInquiry(inquiryId, authUser.getId(), requestDto));
	}

	@PatchMapping("/inquiries/{inquiryId}/update-response")
	public ResponseDto<AdminInquiryResponseDto> updateRespond(@PathVariable Long inquiryId,
		@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody RespondInquiryRequestDto requestDto) {
		return ResponseDto.success(inquiryAdminService.updateRespond(inquiryId, authUser.getId(), requestDto));
	}

	@PatchMapping("/inquiries/status")
	public ResponseDto<AdminInquiryResponseDto> changeInquiryStatus(@Valid @RequestBody InquiryStatusRequestDto requestDto) {
		return ResponseDto.success(inquiryAdminService.changeInquiryStatus(requestDto));
	}

	@GetMapping("/inquiries")
	public ResponseDto<Page<AdminInquiryListResponseDto>> getInquiryList(
		@RequestParam(required = false) String title,
		@RequestParam(required = false) String status,
		@RequestParam(required = false) String writer,
		@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "10") int pageSize) {
		return ResponseDto.success(
			inquiryAdminService.getInquiryList(title, status, writer, pageNumber, pageSize));
	}
}
