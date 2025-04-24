package com.example.junggoheaven.domain.inquiry.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.inquiry.dto.request.CreateInquiryRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.request.UpdateInquiryRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.response.UserInquiryListResponseDto;
import com.example.junggoheaven.domain.inquiry.dto.response.UserInquiryResponseDto;
import com.example.junggoheaven.domain.inquiry.service.InquiryService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InquiryController {

	private final InquiryService inquiryService;

	@PostMapping("/v1/inquiries")
	public ResponseDto<UserInquiryResponseDto> createInquiry(@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody CreateInquiryRequestDto requestDto) {
		return ResponseDto.success(inquiryService.createInquiry(authUser.getId(), requestDto));
	}

	@GetMapping("/v1/inquiries/{inquiryId}")
	public ResponseDto<UserInquiryResponseDto> getInquiry(@AuthenticationPrincipal AuthUser authUser,
		@PathVariable Long inquiryId) {
		return ResponseDto.success(inquiryService.getInquiry(authUser, inquiryId));
	}

	@GetMapping("/v1/inquiries/my")
	public ResponseDto<Page<UserInquiryListResponseDto>> getMyInquiries(@AuthenticationPrincipal AuthUser authUser,
		@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
		return ResponseDto.success(inquiryService.getMyInquiries(authUser.getId(), pageNumber, pageSize));
	}

	@PatchMapping("/v1/inquiries/{inquiryId}")
	public ResponseDto<UserInquiryResponseDto> updateInquiry(@PathVariable Long inquiryId,
		@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody UpdateInquiryRequestDto requestDto) {
		return ResponseDto.success(inquiryService.updateInquiry(inquiryId, authUser.getId(), requestDto));
	}

	@DeleteMapping("/v1/inquiries/{inquiryId}")
	public ResponseDto<Void> deleteInquiry(@PathVariable Long inquiryId, @AuthenticationPrincipal AuthUser authUser) {
		inquiryService.deleteInquiry(inquiryId, authUser.getId());
		return ResponseDto.success(null);
	}
}
